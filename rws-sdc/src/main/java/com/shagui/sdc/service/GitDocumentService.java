package com.shagui.sdc.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.shagui.sdc.api.dto.ServiceDataDTO;
import com.shagui.sdc.api.dto.git.ContentDTO;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponetTypeArchitectureMetricPropertiesModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.util.AnalysisUtils;
import com.shagui.sdc.util.ComponentUtils;
import com.shagui.sdc.util.Ctes.ComponentTypeArchitectureMetricConstants;
import com.shagui.sdc.util.DictioraryReplacement;
import com.shagui.sdc.util.DictioraryReplacement.Replacement;
import com.shagui.sdc.util.IOUtils;
import com.shagui.sdc.util.UrlUtils;
import com.shagui.sdc.util.documents.SdcDocument;
import com.shagui.sdc.util.documents.SdcDocumentFactory;
import com.shagui.sdc.util.documents.data.DocumentServiceDataDTO;
import com.shagui.sdc.util.documents.lib.json.JsonDocument;
import com.shagui.sdc.util.documents.lib.xml.XmlDocument;
import com.shagui.sdc.util.git.GitUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GitDocumentService implements AnalysisInterface {
	private static GitDocumentServiceConfig config;

	protected GitDocumentService() {
	}

	protected abstract Class<? extends SdcDocument> documentOf();

	protected abstract ComponentAnalysisModel executeMetricFn(String fn, DocumentServiceDataDTO data);

	protected static void setConfig(GitDocumentServiceConfig config) {
		GitDocumentService.config = config;
	}

	@Override
	public List<ComponentAnalysisModel> analyze(ComponentModel component) {
		Map<String, List<MetricModel>> metricsByPath = metricsByPath(component);

		return metricsByPath.entrySet().parallelStream()
				.map(entry -> GitUtils
						.retrieveGitData(
								component, "contents/%s".formatted(entry.getKey()), Optional.empty(),
								ContentDTO.class)
						.map(data -> getResponse(component, entry.getValue(), sdcDocument(data))).orElseGet(() -> {
							log.error("Not git info for component '{}'", component.getName());
							return new ArrayList<>();
						}))
				.flatMap(List::stream).toList();
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
		return metrics.stream().map(execute(component, docuemnt)).toList();
	}

	private SdcDocument sdcDocument(ContentDTO gitData) {
		try {
			return sdcDocument(IOUtils.toInputStream(gitData.getDecodedContent()));
		} catch (Exception e) {
			log.error("ERROR in document '{}'.", gitData.getName(), e);
			return sdcDocument(gitData.getDownloadUrl());
		}
	}

	private SdcDocument sdcDocument(String url) {
		try {
			return sdcDocument(UrlUtils.url(url).openStream());
		} catch (Exception e) {
			throw new SdcCustomException("Error reading document from '%s'".formatted(url), e);
		}
	}

	private SdcDocument sdcDocument(InputStream is) throws IOException {
		return SdcDocumentFactory.newInstance(is, documentOf());
	}

	private Map<String, List<MetricModel>> metricsByPath(ComponentModel component) {
		Map<String, List<MetricModel>> paths = new HashMap<>();
		Replacement replacement = DictioraryReplacement.getInstance(ComponentUtils.dictionaryOf(component), true);

		metrics(component).forEach(metric -> {
			Optional<ComponetTypeArchitectureMetricPropertiesModel> property = GitDocumentService.config
					.componentTypeArchitectureMetricPropertiesRepository()
					.repository().findByComponentTypeArchitectureAndMetricAndNameIgnoreCase(
							component.getComponentTypeArchitecture(), metric,
							ComponentTypeArchitectureMetricConstants.PATH);

			String path = property.map(p -> replacement.replace(p.getValue(), ""))
					.map(p -> p.replaceFirst("^/+", ""))
					.orElseThrow(() -> new SdcCustomException(
							"No path of origin has been defined for metric '%s' (%s) component type %s and architecture %s (%s)"
									.formatted(
											metric.getName(), metric.getId(),
											component.getComponentTypeArchitecture().getComponentType(),
											component.getComponentTypeArchitecture().getArchitecture(),
											component.getComponentTypeArchitecture().getId())));

			List<MetricModel> pathMetrics = Optional.ofNullable(paths.get(path)).orElseGet(ArrayList::new);

			if (pathMetrics.isEmpty()) {
				pathMetrics.add(metric);
				paths.put(path, pathMetrics);
			} else {
				pathMetrics.add(metric);
			}
		});

		return paths;
	}

	private Function<MetricModel, ComponentAnalysisModel> execute(ComponentModel component, SdcDocument docuemnt) {
		return metric -> {
			String fn = DictioraryReplacement.fn(metric.getValue()).orElseGet(metric::getValue);

			if (isGenericFn(fn)) {
				String value = MetricLibrary.Library.valueOf(fn.toUpperCase())
						.apply(new DocumentServiceDataDTO(component, metric, docuemnt))
						.orElseGet(AnalysisUtils.notDataFound(new ServiceDataDTO(component, metric)));

				return new ComponentAnalysisModel(component, metric, value);
			} else {
				return executeMetricFn(fn, new DocumentServiceDataDTO(component, metric, docuemnt));
			}
		};
	}

	private boolean isGenericFn(String fn) {
		return Arrays.stream(MetricLibrary.Library.values())
				.anyMatch(libraryValue -> libraryValue.name().equals(fn.toUpperCase()));
	}

	private static class MetricLibrary {
		enum Library {
			GET(get);

			private Function<DocumentServiceDataDTO, Optional<String>> fn;

			private Library(Function<DocumentServiceDataDTO, Optional<String>> fn) {
				this.fn = fn;
			}

			public Optional<String> apply(DocumentServiceDataDTO data) {
				return this.fn.apply(data);
			}
		}

		private MetricLibrary() {
		}

		// Metric library fn's
		private static Function<DocumentServiceDataDTO, Optional<String>> get = serviceData -> {
			Optional<String> key = DictioraryReplacement.value("get", serviceData.getMetric().getValue(), '-', '[', ']',
					'.', '@', '/');
			return key.map(serviceData.getDocuemnt()::value).orElse(Optional.empty());
		};
	}
}
