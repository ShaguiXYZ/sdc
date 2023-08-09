package com.shagui.sdc.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.shagui.sdc.api.client.GitClient;
import com.shagui.sdc.api.dto.git.ContentDTO;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponetTypeArchitectureMetricPropertiesModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.repository.ComponentTypeArchitectureMetricPropertiesRepository;
import com.shagui.sdc.util.ComponentUtils;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.DictioraryReplacement;
import com.shagui.sdc.util.DictioraryReplacement.Replacement;
import com.shagui.sdc.util.IOUtils;
import com.shagui.sdc.util.UrlUtils;
import com.shagui.sdc.util.documents.JsonDocument;
import com.shagui.sdc.util.documents.SdcDocument;
import com.shagui.sdc.util.documents.SdcDocumentFactory;
import com.shagui.sdc.util.documents.XmlDocument;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

import feign.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GitService implements AnalysisInterface {
	@Autowired
	private GitClient gitClient;

	@Autowired
	private ComponentTypeArchitectureMetricPropertiesRepository componentTypeArchitectureMetricPropertiesRep;

	private JpaCommonRepository<ComponentTypeArchitectureMetricPropertiesRepository, ComponetTypeArchitectureMetricPropertiesModel, Integer> componentTypeArchitectureMetricPropertiesRepository;

	protected abstract Class<? extends SdcDocument> documentOf();

	protected GitService() {
		this.componentTypeArchitectureMetricPropertiesRepository = () -> componentTypeArchitectureMetricPropertiesRep;
	}

	@Override
	public List<ComponentAnalysisModel> analyze(ComponentModel component) {
		Map<String, List<MetricModel>> metricPaths = metricPaths(component);

		return metricPaths.entrySet().parallelStream().map(entry -> {
			ContentDTO gitData = retrieveGitData(component, entry.getKey());

			if (gitData == null) {
				log.error(String.format("Not git info for component %s", component.getName()));
				return new ArrayList<ComponentAnalysisModel>();
			}

			SdcDocument docuemnt = sdcDocument(gitData);
			return getResponse(component, entry.getValue(), docuemnt);
		}).flatMap(List::stream).collect(Collectors.toList());
	}

	@Override
	public List<MetricModel> metrics(ComponentModel component) {
		return ComponentUtils.metricsByType(component, type());
	}

	private AnalysisType type() {
		if (documentOf().isAssignableFrom(JsonDocument.class)) {
			return AnalysisType.GIT_JSON;
		} else if (documentOf().isAssignableFrom(XmlDocument.class)) {
			return AnalysisType.GIT_XML;
		} else {
			throw new SdcCustomException("Unsupported document format");
		}
	}

	private List<ComponentAnalysisModel> getResponse(ComponentModel component, List<MetricModel> metrics,
			SdcDocument docuemnt) {
		return metrics.stream().map(metric -> {
			Optional<String> value = docuemnt.fromPath(metric.getValue());
			return new ComponentAnalysisModel(component, metric, value.isPresent() ? value.get() : "N/A");
		}).collect(Collectors.toList());
	}

	private ContentDTO retrieveGitData(ComponentModel component, String path) {
		Optional<UriModel> uri = uri(component, path);

		if (uri.isPresent()) {
			Optional<String> authorizationHeader = authorization(uri.get());

			Response response = authorizationHeader.isPresent()
					? gitClient.repoFile(URI.create(uri.get().getValue()), authorizationHeader.get())
					: gitClient.repoFile(URI.create(uri.get().getValue()));

			return UrlUtils.mapResponse(response, ContentDTO.class);
		}

		return null;
	}

	private Optional<String> authorization(UriModel uriModel) {
		Optional<String> authorization = UrlUtils.uriProperty(uriModel, Ctes.URI_PROPERTIES.AUTHORIZATION);

		return authorization.map(data -> DictioraryReplacement.getInstance(ComponentUtils.tokens()).replace(data, ""));
	}

	private SdcDocument sdcDocument(ContentDTO gitData) {
		try {
			return sdcDocument(IOUtils.toInputStream(gitData.getDecodedContent()));
		} catch (Exception e) {
			log.error(String.format("ERROR in document '%s'.", gitData.getName()), e);
			return sdcDocument(gitData.getDownloadUrl());
		}
	}

	private SdcDocument sdcDocument(String url) {
		try {
			return sdcDocument(UrlUtils.url(url).openStream());
		} catch (Exception e) {
			throw new SdcCustomException(String.format("Error reading document from '%s'", url), e);
		}
	}

	private SdcDocument sdcDocument(InputStream is) throws IOException {
		return SdcDocumentFactory.newInstance(is, documentOf());
	}

	private Optional<UriModel> uri(ComponentModel component, String path) {
		return UrlUtils.componentUri(component, UriType.GIT).map(model -> {
			String uri = Arrays.asList(model.getValue(), path).stream().filter(StringUtils::hasText)
					.collect(Collectors.joining("/"));
			model.setValue(uri);

			return model;
		});
	}

	private Map<String, List<MetricModel>> metricPaths(ComponentModel component) {
		Map<String, List<MetricModel>> paths = new HashMap<>();
		Replacement replacement = DictioraryReplacement.getInstance(ComponentUtils.dictionaryOf(component), true);

		metrics(component).forEach(metric -> {
			Optional<ComponetTypeArchitectureMetricPropertiesModel> data = componentTypeArchitectureMetricPropertiesRepository
					.repository().findByComponentTypeArchitectureAndMetricAndName(
							component.getComponentTypeArchitecture(), metric, "PATH");

			if (data.isPresent()) {
				String path = replacement.replace(data.get().getValue(), "");
				List<MetricModel> pathMetrics = Optional.ofNullable(paths.get(path)).orElseGet(ArrayList::new);

				if (pathMetrics.isEmpty()) {
					pathMetrics.add(metric);
					paths.put(path, pathMetrics);
				} else {
					pathMetrics.add(metric);
				}
			}

		});

		return paths;
	}
}
