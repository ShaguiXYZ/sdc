package com.shagui.sdc.service.impl;

import java.util.List;
import java.util.stream.Stream;

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
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.MetricValuesModel;
import com.shagui.sdc.repository.ComponentTypeArchitectureRepository;
import com.shagui.sdc.repository.MetricRepository;
import com.shagui.sdc.repository.MetricValueRepository;
import com.shagui.sdc.service.ComponentTypeArchitectureService;
import com.shagui.sdc.util.ComponentTypeArchitectureUtils;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

@Service
public class ComponentTypeArchitectureServiceImpl implements ComponentTypeArchitectureService {
	private JpaCommonRepository<ComponentTypeArchitectureRepository, ComponentTypeArchitectureModel, Integer> componentTypeArchitectureRepository;
	private JpaCommonRepository<MetricRepository, MetricModel, Integer> metricRepository;
	private JpaCommonRepository<MetricValueRepository, MetricValuesModel, Integer> metricValueRepository;

	public ComponentTypeArchitectureServiceImpl(
			final ComponentTypeArchitectureRepository componentTypeArchitectureRepository,
			final MetricRepository metricRepository,
			final MetricValueRepository metricValueRepository) {
		this.componentTypeArchitectureRepository = () -> componentTypeArchitectureRepository;
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
		ComponentTypeArchitectureUtils.normalizeData(model).accept(Mapper.parse(data));

		return Mapper.parse(componentTypeArchitectureRepository.update(componentTypeArchitectureId, model));
	}

	@Transactional
	@Override
	public List<ComponentTypeArchitectureDTO> create(List<ComponentTypeArchitectureDTO> data) {
		List<ComponentTypeArchitectureModel> models = data.stream().map(Mapper::parse).toList();

		models.forEach(ComponentTypeArchitectureUtils::normalizeData);

		return models.stream()
				.map(componentTypeArchitectureRepository::create).map(Mapper::parse)
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
						.orElseThrow(ComponentTypeArchitectureUtils.notMetricFound(metricProperty)))
				.toList();

		return componentTypeArchitectures.stream()
				.map(ComponentTypeArchitectureUtils.patchMetrics(metrics))
				.map(ComponentTypeArchitectureUtils.saveMetricProperties(metricProperties, metrics))
				.map(Mapper::parse).toList();
	}

	@Override
	public List<MetricValuesOutDTO> defineMetricValues(String componentType, String architecture, String metricName,
			AnalysisType metricType, MetricValuesDTO values) {
		MetricModel metric = metricRepository.repository().findByNameIgnoreCaseAndType(metricName, metricType)
				.orElseThrow(() -> new SdcCustomException("Metric '%s' Not found".formatted(metricName)));

		Stream<ComponentTypeArchitectureModel> componentTypeArchitectures$ = componentTypeArchitectures(componentType,
				architecture).stream()
				.filter(componentTypeArchitecture -> componentTypeArchitecture.getMetrics().stream()
						.anyMatch(caMetric -> caMetric.getId().equals(metric.getId())));

		return componentTypeArchitectures$.map(componentTypeArchitecture -> {
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
}
