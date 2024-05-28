package com.shagui.sdc.util;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.shagui.sdc.api.dto.MetricPropertiesDTO;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.model.DataLists;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.ComponetTypeArchitectureMetricPropertiesModel;
import com.shagui.sdc.model.MetricModel;

public class ComponentTypeArchitectureUtils {
	private static ComponentTypeArchitectureUtilsConfig config;

	private ComponentTypeArchitectureUtils() {
	}

	protected static void setConfig(ComponentTypeArchitectureUtilsConfig config) {
		ComponentTypeArchitectureUtils.config = config;
	}

	public static ComponentTypeArchitectureModel patchMetrics(
			ComponentTypeArchitectureModel componentTypeArchitecture, List<MetricModel> metrics) {
		List<MetricModel> metricsToSave = metrics.stream()
				.filter(metric -> componentTypeArchitecture.getMetrics()
						.stream().noneMatch(m -> Objects.equals(m.getId(), metric.getId())))
				.toList();

		if (metricsToSave.isEmpty()) {
			return componentTypeArchitecture;
		} else {
			componentTypeArchitecture.getMetrics().addAll(metricsToSave);
			return config.componentTypeArchitectureRepository().saveAndFlush(componentTypeArchitecture);
		}
	}

	public static ComponentTypeArchitectureModel saveMetricProperties(
			ComponentTypeArchitectureModel componentTypeArchitecture,
			List<MetricPropertiesDTO> properties, List<MetricModel> metrics) {

		properties.forEach(property -> {
			MetricModel metric = metrics.stream().filter(
					data -> Objects.equals(data.getName().toLowerCase(),
							property.getMetricName().toLowerCase())
							&& Objects.equals(data.getType(), property.getType()))
					.findFirst().orElseThrow(notMetricFound(property));

			saveComponentTypeArchitectureMetricProperties(componentTypeArchitecture, metric,
					property.getParams());
		});

		return componentTypeArchitecture;
	}

	public static UnaryOperator<ComponentTypeArchitectureModel> patchMetrics(
			List<MetricModel> metrics) {
		return componentTypeArchitecture -> patchMetrics(componentTypeArchitecture,
				metrics);
	}

	public static UnaryOperator<ComponentTypeArchitectureModel> saveMetricProperties(
			List<MetricPropertiesDTO> properties,
			List<MetricModel> metrics) {
		return componentTypeArchitecture -> saveMetricProperties(componentTypeArchitecture, properties,
				metrics);
	}

	public static Consumer<ComponentTypeArchitectureModel> normalizeData(ComponentTypeArchitectureModel source) {
		return model -> {
			model.setArchitecture(StaticRepository.datalistValues(DataLists.ARCHITECTURES).stream().filter(
					architecture -> Objects.equals(architecture.toLowerCase(),
							source.getArchitecture().toLowerCase()))
					.findFirst().orElseThrow(
							() -> new SdcCustomException(
									"Architecture '%s' Not found".formatted(
											source.getArchitecture()))));

			model.setComponentType(StaticRepository
					.datalistValues(DataLists.COMPONENT_TYPES).stream().filter(
							componentType -> Objects.equals(componentType.toLowerCase(),
									source.getComponentType().toLowerCase()))
					.findFirst().orElseThrow(
							() -> new SdcCustomException(
									"Component Type '%s' Not found".formatted(
											source.getComponentType()))));

			model.setDeploymentType(StaticRepository
					.datalistValues(DataLists.DEPLOYMENT_TYPES).stream().filter(
							deploymentType -> Objects.equals(deploymentType.toLowerCase(),
									source.getDeploymentType().toLowerCase()))
					.findFirst().orElseThrow(
							() -> new SdcCustomException(
									"Deployment Type '%s' Not found".formatted(
											source.getDeploymentType()))));

			model.setLanguage(StaticRepository.datalistValues(DataLists.LANGUAGES).stream().filter(
					language -> Objects.equals(language.toLowerCase(),
							source.getLanguage().toLowerCase()))
					.findFirst()
					.orElseThrow(
							() -> new SdcCustomException("Language '%s' Not found"
									.formatted(source.getLanguage()))));

			model.setNetwork(StaticRepository.datalistValues(DataLists.NETWORKS).stream().filter(
					network -> Objects.equals(network.toLowerCase(),
							source.getNetwork().toLowerCase()))
					.findFirst()
					.orElseThrow(
							() -> new SdcCustomException("Network '%s' Not found"
									.formatted(source.getNetwork()))));

			model.setPlatform(StaticRepository.datalistValues(DataLists.PLATFORMS).stream().filter(
					platform -> Objects.equals(platform.toLowerCase(),
							source.getPlatform().toLowerCase()))
					.findFirst()
					.orElseThrow(
							() -> new SdcCustomException("Platform '%s' Not found"
									.formatted(source.getPlatform()))));
		};
	}

	public static Supplier<SdcCustomException> notMetricFound(MetricPropertiesDTO property) {
		return () -> new SdcCustomException(
				"Not metric '%s' (%s) found".formatted(property.getMetricName(), property.getType()));
	}

	private static List<ComponetTypeArchitectureMetricPropertiesModel> saveComponentTypeArchitectureMetricProperties(
			ComponentTypeArchitectureModel componentTypeArchitecture,
			MetricModel metric, Map<String, String> properties) {
		return properties.entrySet().stream().map(entry -> {
			ComponetTypeArchitectureMetricPropertiesModel property = config
					.componentTypeArchitectureMetricPropertiesRepository()
					.repository()
					.findByComponentTypeArchitectureAndMetricAndNameIgnoreCase(
							componentTypeArchitecture,
							metric, entry.getKey())
					.orElseGet(() -> {
						ComponetTypeArchitectureMetricPropertiesModel newProperty = new ComponetTypeArchitectureMetricPropertiesModel();
						newProperty.setComponentTypeArchitecture(componentTypeArchitecture);
						newProperty.setMetric(metric);
						newProperty.setName(entry.getKey());

						return newProperty;
					});

			property.setValue(entry.getValue());

			return property;
		}).map(config.componentTypeArchitectureMetricPropertiesRepository()::save).toList();
	}
}
