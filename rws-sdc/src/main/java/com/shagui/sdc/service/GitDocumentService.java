package com.shagui.sdc.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import com.shagui.sdc.api.dto.ServiceDataDTO;
import com.shagui.sdc.api.dto.git.ContentDTO;
import com.shagui.sdc.api.dto.sse.EventFactory;
import com.shagui.sdc.api.dto.sse.EventType;
import com.shagui.sdc.core.exception.SdcCustomException;
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
import com.shagui.sdc.util.git.GitUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GitDocumentService implements AnalysisInterface {
	private static GitDocumentServiceConfig config;

	protected static void setConfig(GitDocumentServiceConfig config) {
		GitDocumentService.config = config;
	}

	protected abstract Class<? extends SdcDocument> documentOf();

	protected abstract ComponentAnalysisModel executeMetricFn(String fn, DocumentServiceDataDTO data);

	protected GitDocumentService() {
	}

	@Override
	public List<ComponentAnalysisModel> analyze(String workflowId, ComponentModel component) {
		Map<String, List<MetricModel>> metricsByPath = metricsByPath(workflowId, component);

		return metricsByPath.entrySet().parallelStream()
				.map(entry -> {
					try {
						return GitUtils
								.retrieveGitData(component, "contents/%s".formatted(entry.getKey()),
										Optional.empty(), ContentDTO.class)
								.map(data -> getResponse(workflowId, component, entry.getValue(), sdcDocument(data)))
								.orElseThrow(() -> new SdcCustomException(
										"Not git info for component '%s'".formatted(component.getName())));
					} catch (SdcCustomException ex) {
						config.sseService().emitError(EventFactory.event(workflowId, ex).referencedBy(component));

						return null;
					}
				}).filter(Objects::nonNull).flatMap(List::stream).toList();
	}

	private List<ComponentAnalysisModel> getResponse(String workflowId, ComponentModel component,
			List<MetricModel> metrics, SdcDocument docuemnt) {
		return metrics.stream().map(execute(workflowId, component, docuemnt)).toList();
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

	private Map<String, List<MetricModel>> metricsByPath(String workflowId, ComponentModel component) {
		Map<String, List<MetricModel>> paths = new HashMap<>();
		Replacement replacement = DictioraryReplacement.getInstance(ComponentUtils.dictionaryOf(component), true);

		metrics(component).forEach(metric -> {
			ComponetTypeArchitectureMetricPropertiesModel property = GitDocumentService.config
					.componentTypeArchitectureMetricPropertiesRepository()
					.repository().findByComponentTypeArchitectureAndMetricAndNameIgnoreCase(
							component.getComponentTypeArchitecture(), metric,
							ComponentTypeArchitectureMetricConstants.PATH)
					.orElseGet(() -> {
						config.sseService().emitError(EventFactory
								.event(workflowId, EventType.ERROR,
										"No path of origin has been defined for metric '%s' (%s) component type '%s' and architecture '%s' (%s)"
												.formatted(
														metric.getName(), metric.getId(),
														component.getComponentTypeArchitecture()
																.getComponentType(),
														component.getComponentTypeArchitecture()
																.getArchitecture(),
														component.getComponentTypeArchitecture().getId()))
								.referencedBy(component, metric));

						return null;
					});

			if (property == null) {
				return;
			}

			try {
				String path = replacement.replace(property.getValue(), "").replaceFirst("^/+", "");
				List<MetricModel> pathMetrics = Optional.ofNullable(paths.get(path)).orElseGet(ArrayList::new);

				if (pathMetrics.isEmpty()) {
					pathMetrics.add(metric);
					paths.put(path, pathMetrics);
				} else {
					pathMetrics.add(metric);
				}
			} catch (SdcCustomException ex) {
				config.sseService().emitError(EventFactory.event(workflowId, ex).referencedBy(component, metric));
			}
		});

		return paths;
	}

	private Function<MetricModel, ComponentAnalysisModel> execute(String workflowId, ComponentModel component,
			SdcDocument docuemnt) {
		return metric -> {
			String fn = DictioraryReplacement.fn(metric.getValue()).orElseGet(metric::getValue);

			if (isGenericFn(fn)) {
				String value = MetricLibrary.Library.valueOf(fn.toUpperCase())
						.apply(new DocumentServiceDataDTO(workflowId, component, metric, docuemnt))
						.orElseGet(AnalysisUtils.notDataFound(new ServiceDataDTO(workflowId, component, metric)));

				return new ComponentAnalysisModel(component, metric, value);
			} else {
				return executeMetricFn(fn, new DocumentServiceDataDTO(workflowId, component, metric, docuemnt));
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
