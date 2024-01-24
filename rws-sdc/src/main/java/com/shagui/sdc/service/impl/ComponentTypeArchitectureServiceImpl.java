package com.shagui.sdc.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentTypeArchitectureDTO;
import com.shagui.sdc.api.dto.MetricPropertiesDTO;
import com.shagui.sdc.api.dto.MetricValuesDTO;
import com.shagui.sdc.api.dto.MetricValuesOutDTO;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.model.DataLists;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.ComponetTypeArchitectureMetricPropertiesModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.MetricValuesModel;
import com.shagui.sdc.repository.ComponentTypeArchitectureMetricPropertiesRepository;
import com.shagui.sdc.repository.ComponentTypeArchitectureRepository;
import com.shagui.sdc.repository.MetricRepository;
import com.shagui.sdc.repository.MetricValueRepository;
import com.shagui.sdc.service.ComponentTypeArchitectureService;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

@Service
public class ComponentTypeArchitectureServiceImpl implements ComponentTypeArchitectureService {
	private JpaCommonRepository<ComponentTypeArchitectureRepository, ComponentTypeArchitectureModel, Integer> componentTypeArchitectureRepository;
	private JpaCommonRepository<ComponentTypeArchitectureMetricPropertiesRepository, ComponetTypeArchitectureMetricPropertiesModel, Integer> componentTypeArchitectureMetricPropertiesRepository;
	private JpaCommonRepository<MetricRepository, MetricModel, Integer> metricRepository;
	private JpaCommonRepository<MetricValueRepository, MetricValuesModel, Integer> metricValueRepository;

	public ComponentTypeArchitectureServiceImpl(ComponentTypeArchitectureRepository componentTypeArchitectureRepository,
			ComponentTypeArchitectureMetricPropertiesRepository componentTypeArchitectureMetricPropertiesRepository,
			MetricRepository metricRepository,
			MetricValueRepository metricValueRepository) {
		this.componentTypeArchitectureRepository = () -> componentTypeArchitectureRepository;
		this.componentTypeArchitectureMetricPropertiesRepository = () -> componentTypeArchitectureMetricPropertiesRepository;
		this.metricRepository = () -> metricRepository;
		this.metricValueRepository = () -> metricValueRepository;
	}

	@Override
	public PageData<ComponentTypeArchitectureDTO> filter(String componentType, String architecture, String network,
			String deploymentType, String platform, String language) {
		return componentTypeArchitectureRepository.repository()
				.findBy(componentType == null ? null : componentType.toLowerCase(),
						architecture == null ? null : architecture.toLowerCase(),
						network == null ? null : network.toLowerCase(),
						deploymentType == null ? null : deploymentType.toLowerCase(),
						platform == null ? null : platform.toLowerCase(),
						language == null ? null : language.toLowerCase())
				.stream()
				.map(Mapper::parse).collect(SdcCollectors.toPageable());
	}

	@Override
	public ComponentTypeArchitectureDTO update(int componentTypeArchitectureId, ComponentTypeArchitectureDTO data) {
		ComponentTypeArchitectureModel model = componentTypeArchitectureRepository.findExistingId(data.getId());

		model.setArchitecture(StaticRepository.datalistValues(DataLists.ARCHITECTURES).stream().filter(
				architecture -> Objects.equals(architecture.toLowerCase(), data.getArchitecture().toLowerCase()))
				.findFirst().orElse(model.getArchitecture()));

		model.setComponentType(StaticRepository.datalistValues(DataLists.COMPONENT_TYPES).stream().filter(
				componentType -> Objects.equals(componentType.toLowerCase(), data.getComponentType().toLowerCase()))
				.findFirst().orElse(model.getComponentType()));

		model.setDeploymentType(StaticRepository.datalistValues(DataLists.DEPLOYMENT_TYPES).stream().filter(
				deploymentType -> Objects.equals(deploymentType.toLowerCase(), data.getDeploymentType().toLowerCase()))
				.findFirst().orElse(model.getDeploymentType()));

		model.setLanguage(StaticRepository.datalistValues(DataLists.LANGUAGES).stream().filter(
				language -> Objects.equals(language.toLowerCase(), data.getLanguage().toLowerCase())).findFirst()
				.orElse(model.getLanguage()));

		model.setNetwork(StaticRepository.datalistValues(DataLists.NETWORKS).stream().filter(
				network -> Objects.equals(network.toLowerCase(), data.getNetwork().toLowerCase())).findFirst()
				.orElse(model.getNetwork()));

		model.setPlatform(StaticRepository.datalistValues(DataLists.PLATFORMS).stream().filter(
				platform -> Objects.equals(platform.toLowerCase(), data.getPlatform().toLowerCase())).findFirst()
				.orElse(model.getPlatform()));

		return Mapper
				.parse(componentTypeArchitectureRepository.update(componentTypeArchitectureId, model));
	}

	@Transactional
	@Override
	public List<ComponentTypeArchitectureDTO> create(List<ComponentTypeArchitectureDTO> data) {
		return data.stream().map(Mapper::parse).map(componentTypeArchitectureRepository::create).map(Mapper::parse)
				.toList();
	}

	@Transactional
	@Override
	public List<ComponentTypeArchitectureDTO> componentTypeArchitectureMetrics(String componentType,
			String architecture, List<MetricPropertiesDTO> metricProperties) {
		List<ComponentTypeArchitectureModel> componentTypeArchitectures = componentTypeArchitectures(componentType,
				architecture);

		List<MetricModel> metrics = metricProperties.stream()
				.map(metricProperty -> metricRepository.repository()
						.findByNameIgnoreCaseAndType(metricProperty.getMetricName(), metricProperty.getType())
						.orElseThrow(notMetricFound(metricProperty)))
				.toList();

		return componentTypeArchitectures.stream()
				.map(patchComponentTypeArchitectureMetrics(metrics))
				.map(saveMetricProperties(metricProperties, metrics))
				.map(Mapper::parse).toList();
	}

	@Override
	public List<MetricValuesOutDTO> defineMetricValues(String componentType, String architecture, String metricName,
			AnalysisType metricType, MetricValuesDTO values) {
		MetricModel metric = metricRepository.repository().findByNameIgnoreCaseAndType(metricName, metricType)
				.orElseThrow(() -> new SdcCustomException("Metric '%s' Not found".formatted(metricName)));

		List<ComponentTypeArchitectureModel> componentTypeArchitectures = componentTypeArchitectures(componentType,
				architecture).stream()
				.filter(componentTypeArchitecture -> componentTypeArchitecture.getMetrics().stream()
						.anyMatch(caMetric -> caMetric.getId().equals(metric.getId())))
				.toList();

		return componentTypeArchitectures.stream().map(componentTypeArchitecture -> {
			MetricValuesModel model = new MetricValuesModel();
			model.setComponentTypeArchitecture(componentTypeArchitecture);
			model.setMetric(metric);
			BeanUtils.copyProperties(values, model);

			return model;
		}).map(metricValueRepository::create).map(MetricValuesOutDTO::new).toList();
	}

	private List<ComponentTypeArchitectureModel> componentTypeArchitectures(String componentType, String architecture) {
		if (!StringUtils.hasText(componentType) && !StringUtils.hasText(architecture)) {
			throw new SdcCustomException("component type or architecture are mandatory");
		}

		if (StringUtils.hasText(componentType) && StringUtils.hasText(architecture)) {
			return componentTypeArchitectureRepository.repository().findByComponentTypeAndArchitecture(componentType,
					architecture);
		} else if (StringUtils.hasText(componentType)) {
			return componentTypeArchitectureRepository.repository().findByComponentType(componentType);
		} else {
			return componentTypeArchitectureRepository.repository().findByArchitecture(architecture);
		}
	}

	private UnaryOperator<ComponentTypeArchitectureModel> patchComponentTypeArchitectureMetrics(
			List<MetricModel> metrics) {
		return componentTypeArchitecture -> {
			List<MetricModel> metricsToSave = metrics.stream().filter(metric -> componentTypeArchitecture.getMetrics()
					.stream().noneMatch(m -> Objects.equals(m.getId(), metric.getId()))).toList();

			if (metricsToSave.isEmpty()) {
				return componentTypeArchitecture;
			} else {
				componentTypeArchitecture.getMetrics().addAll(metricsToSave);
				return componentTypeArchitectureRepository.saveAndFlush(componentTypeArchitecture);
			}
		};
	}

	private UnaryOperator<ComponentTypeArchitectureModel> saveMetricProperties(List<MetricPropertiesDTO> properties,
			List<MetricModel> metrics) {
		return componentTypeArchitecture -> {
			properties.forEach(property -> {
				MetricModel metric = metrics.stream().filter(
						data -> Objects.equals(data.getName().toLowerCase(), property.getMetricName().toLowerCase())
								&& Objects.equals(data.getType(), property.getType()))
						.findFirst().orElseThrow(notMetricFound(property));

				saveComponentTypeArchitectureMetricProperties(componentTypeArchitecture, metric, property.getParams());
			});

			return componentTypeArchitecture;
		};
	}

	private List<ComponetTypeArchitectureMetricPropertiesModel> saveComponentTypeArchitectureMetricProperties(
			ComponentTypeArchitectureModel componentTypeArchitecture,
			MetricModel metric, Map<String, String> properties) {

		return properties.entrySet().stream().map(entry -> {
			ComponetTypeArchitectureMetricPropertiesModel property = componentTypeArchitectureMetricPropertiesRepository
					.repository().findByComponentTypeArchitectureAndMetricAndNameIgnoreCase(componentTypeArchitecture,
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
		}).map(componentTypeArchitectureMetricPropertiesRepository::save).toList();
	}

	private Supplier<SdcCustomException> notMetricFound(MetricPropertiesDTO property) {
		return () -> new SdcCustomException(
				"Not metric '%s' (%s) found".formatted(property.getMetricName(), property.getType()));
	}
}
