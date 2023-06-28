package com.shagui.sdc.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.shagui.sdc.api.client.GitClient;
import com.shagui.sdc.api.dto.git.ContentDTO;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.util.ComponentUtils;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.DictioraryReplacement;
import com.shagui.sdc.util.IOUtils;
import com.shagui.sdc.util.UrlUtils;
import com.shagui.sdc.util.documents.JsonDocument;
import com.shagui.sdc.util.documents.SdcDocument;
import com.shagui.sdc.util.documents.SdcDocumentFactory;
import com.shagui.sdc.util.documents.XmlDocument;

import feign.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GitService implements AnalysisInterface {
	@Autowired
	private GitClient gitClient;

	protected abstract Class<? extends SdcDocument> documentOf();
	
	protected String documentPathProperty() {
		if (documentOf().isAssignableFrom(JsonDocument.class)) {
			return Ctes.COMPONENT_PROPERTIES.JSON_PATH;
		} else if (documentOf().isAssignableFrom(XmlDocument.class)) {
			return  Ctes.COMPONENT_PROPERTIES.XML_PATH;
		} else {
			throw new SdcCustomException("Unsupported document format");
		}
	}

	protected AnalysisType type() {
		if (documentOf().isAssignableFrom(JsonDocument.class)) {
			return AnalysisType.GIT_JSON;
		} else if (documentOf().isAssignableFrom(XmlDocument.class)) {
			return AnalysisType.GIT_XML;
		} else {
			throw new SdcCustomException("Unsupported document format");
		}
	}

	public List<ComponentAnalysisModel> analyze(ComponentModel component) {
		List<MetricModel> gitMetrics = metrics(component);
		ContentDTO gitData = null;

		if (!gitMetrics.isEmpty() && (gitData = retrieveGitData(component)) != null) {
			SdcDocument docuemnt = sdcDocument(gitData);
			return getResponse(component, gitMetrics, docuemnt);
		}

		return new ArrayList<>();
	}

	public Optional<String> uri(ComponentModel component) {
		String uri = null;
		Optional<UriModel> uriModel = uri(component.getUris(), AnalysisType.GIT);

		if (uriModel.isPresent()) {
			String path = ComponentUtils.propertyValue(component, documentPathProperty());
			String owner = ComponentUtils.propertyValue(component, Ctes.COMPONENT_PROPERTIES.COMPONENT_OWNER);
			String repository = ComponentUtils.propertyValue(component, Ctes.COMPONENT_PROPERTIES.COMPONENT_REPOSITORY);

			uri = uriModel.get().getValue();
			uri = Arrays.asList(uri, owner, repository, "contents", path).stream().filter(StringUtils::hasText)
					.collect(Collectors.joining("/"));
		}

		return Optional.ofNullable(uri);
	}

	public List<MetricModel> metrics(ComponentModel component) {
		return ComponentUtils.metricsByType(component, type());
	}

	private List<ComponentAnalysisModel> getResponse(ComponentModel component, List<MetricModel> metrics,
			SdcDocument docuemnt) {
		List<ComponentAnalysisModel> response = new ArrayList<>();

		metrics.forEach(metric -> {
			Optional<String> value = docuemnt.fromPath(metric.getName());
			response.add(new ComponentAnalysisModel(component, metric, value.isPresent() ? value.get() : "N/A"));
		});

		return response;
	}

	private ContentDTO retrieveGitData(ComponentModel component) {
		Optional<String> uri = uri(component);

		if (uri.isPresent()) {
			Optional<String> authorizationHeader = authorization(component);

			Response response = authorizationHeader.isPresent()
					? gitClient.repoFile(URI.create(uri.get()), authorizationHeader.get())
					: gitClient.repoFile(URI.create(uri.get()));

			return UrlUtils.mapResponse(response, ContentDTO.class);
		}

		return null;
	}

	private Optional<String> authorization(ComponentModel component) {
		String authorization = null;
		Optional<UriModel> uriModel = uri(component.getUris(), AnalysisType.GIT);

		if (uriModel.isPresent()) {
			authorization = uriProperty(uriModel.get(), Ctes.URI_PROPERTIES.AUTHORIZATION).orElse(null);
		}

		return Optional.ofNullable(authorization)
				.map(data -> DictioraryReplacement.getInstance(ComponentUtils.tokens()).replace(data));
	}

	private SdcDocument sdcDocument(ContentDTO gitData) {
		try {
			return sdcDocument(IOUtils.stringInputStream(gitData.getDecodedContent()));
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

	private SdcDocument sdcDocument(InputStream is) throws IOException  {
		return SdcDocumentFactory.newInstance(is, documentOf());
	}
}
