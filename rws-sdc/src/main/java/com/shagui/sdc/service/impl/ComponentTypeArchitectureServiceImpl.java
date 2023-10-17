package com.shagui.sdc.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

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
	private JpaCommonRepository<MetricRepository, MetricModel, Integer> metricRepository;
	private JpaCommonRepository<ComponentTypeArchitectureMetricPropertiesRepository, ComponetTypeArchitectureMetricPropertiesModel, Integer> componentTypeArchitectureMetricPropertiesRepository;
	private JpaCommonRepository<MetricValueRepository, MetricValuesModel, Integer> metricValueRepository;

	public ComponentTypeArchitectureServiceImpl(ComponentTypeArchitectureRepository componentTypeArchitectureRepository,
			MetricRepository metricRepository,
			ComponentTypeArchitectureMetricPropertiesRepository componentTypeArchitectureMetricPropertiesRepository,
			MetricValueRepository metricValueRepository) {
		this.componentTypeArchitectureRepository = () -> componentTypeArchitectureRepository;
		this.metricRepository = () -> metricRepository;
		this.componentTypeArchitectureMetricPropertiesRepository = () -> componentTypeArchitectureMetricPropertiesRepository;
		this.metricValueRepository = () -> metricValueRepository;
	}

	@Override
	public PageData<ComponentTypeArchitectureDTO> filter(String componentType, String architecture, String network,
			String deploymentType, String platform, String language) {
		return componentTypeArchitectureRepository.repository()
				.findBy(componentType, architecture, network, deploymentType, platform, language)
				.stream()
				.map(Mapper::parse).collect(SdcCollectors.toPageable());
	}

	@Override
	public ComponentTypeArchitectureDTO update(int componentTypeArchitectureId, ComponentTypeArchitectureDTO data) {
		return Mapper
				.parse(componentTypeArchitectureRepository.update(componentTypeArchitectureId, Mapper.parse(data)));

	}

	@Transactional
	@Override
	public List<ComponentTypeArchitectureDTO> create(List<ComponentTypeArchitectureDTO> data) {
		return data.stream().map(Mapper::parse).map(componentTypeArchitectureRepository::create).map(Mapper::parse)
				.collect(Collectors.toList());
	}

	@Override
	public List<ComponentTypeArchitectureDTO> componentTypeArchitectureMetrics(String componentType,
			String architecture, List<MetricPropertiesDTO> metricProperties) {
		List<ComponentTypeArchitectureModel> componentTypeArchitectures = componentTypeArchitectures(componentType,
				architecture);

		List<MetricModel> metrics = metricProperties.stream()
				.map(property -> metricRepository.repository()
						.findByNameIgnoreCaseAndType(property.getMetricName(), property.getType())
						.orElseThrow(notMetricFound(property)))
				.collect(Collectors.toList());

		List<ComponentTypeArchitectureModel> models = componentTypeArchitectures.stream()
				.map(patchComponentTypeArchitectureMetrics(metrics))
				.map(saveMetricProperties(metricProperties, metrics)).collect(Collectors.toList());

		return models.stream().map(Mapper::parse).collect(Collectors.toList());
	}

	@Override
	public List<MetricValuesOutDTO> defineMetricValues(String componentType, String architecture, String metricName,
			AnalysisType metricType, MetricValuesDTO values) {
		MetricModel metric = metricRepository.repository().findByNameIgnoreCaseAndType(metricName, metricType)
				.orElseThrow(() -> new SdcCustomException(String.format("Metric '%s' Not found", metricName)));

		List<ComponentTypeArchitectureModel> componentTypeArchitectures = componentTypeArchitectures(componentType,
				architecture).stream()
				.filter(componentTypeArchitecture -> componentTypeArchitecture.getMetrics().stream()
						.anyMatch(caMetric -> caMetric.getId().equals(metric.getId())))
				.collect(Collectors.toList());

		return componentTypeArchitectures.stream().map(componentTypeArchitecture -> {
			MetricValuesModel model = new MetricValuesModel();
			model.setComponentTypeArchitecture(componentTypeArchitecture);
			model.setMetric(metric);
			BeanUtils.copyProperties(values, model);

			return model;
		}).map(metricValueRepository::create).map(MetricValuesOutDTO::new).collect(Collectors.toList());
	}

	private List<ComponentTypeArchitectureModel> componentTypeArchitectures(String componentType, String architecture) {
		if (StringUtils.hasText(componentType) && StringUtils.hasText(architecture)) {
			return componentTypeArchitectureRepository.repository().findByComponentTypeAndArchitecture(componentType,
					architecture);
		} else if (StringUtils.hasText(componentType)) {
			return componentTypeArchitectureRepository.repository().findByComponentType(componentType);
		} else if (StringUtils.hasText(architecture)) {
			return componentTypeArchitectureRepository.repository().findByArchitecture(architecture);
		} else {
			throw new SdcCustomException("component type or architecture are mandatory");
		}
	}

	private UnaryOperator<ComponentTypeArchitectureModel> patchComponentTypeArchitectureMetrics(
			List<MetricModel> metrics) {
		return componentTypeArchitecture -> {
			List<MetricModel> metricsToSave = metrics.stream().filter(metric -> componentTypeArchitecture.getMetrics()
					.stream().noneMatch(m -> Objects.equals(m.getId(), metric.getId()))).collect(Collectors.toList());

			if (metricsToSave.isEmpty()) {
				return componentTypeArchitecture;
			} else {
				componentTypeArchitecture.getMetrics().addAll(metricsToSave);
				return componentTypeArchitectureRepository.save(componentTypeArchitecture);
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

	private void saveComponentTypeArchitectureMetricProperties(ComponentTypeArchitectureModel componentTypeArchitecture,
			MetricModel metric, Map<String, String> properties) {

		properties.entrySet().stream().forEach(entry -> {
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

			componentTypeArchitectureMetricPropertiesRepository.save(property);
		});
	}

	private Supplier<SdcCustomException> notMetricFound(MetricPropertiesDTO property) {
		return () -> new SdcCustomException(
				String.format("Not metric '%s' (%s) found", property.getMetricName(), property.getType()));
	}
}
