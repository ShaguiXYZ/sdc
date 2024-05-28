package com.shagui.sdc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.ComponentTypeArchitectureApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentTypeArchitectureDTO;
import com.shagui.sdc.api.dto.MetricPropertiesDTO;
import com.shagui.sdc.api.dto.MetricValuesDTO;
import com.shagui.sdc.api.dto.MetricValuesOutDTO;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.service.ComponentTypeArchitectureService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "component-type-architecture", description = "API to maintain Components type architectures")
public class ComponentTypeArchitectureController implements ComponentTypeArchitectureApi {
	private ComponentTypeArchitectureService componentTypeArchitectureService;

	@Override
	public PageData<ComponentTypeArchitectureDTO> componentTypeArchitecture(String componentType, String architecture,
			String network, String deploymentType, String platform, String language) {
		return componentTypeArchitectureService
				.filter(componentType, architecture, network, deploymentType, platform, language);
	}

	@Override
	public ComponentTypeArchitectureDTO update(int componentTypeArchitectureId, ComponentTypeArchitectureDTO data) {
		return componentTypeArchitectureService.update(componentTypeArchitectureId, data);
	}

	@Override
	public List<ComponentTypeArchitectureDTO> create(List<ComponentTypeArchitectureDTO> data) {
		return componentTypeArchitectureService.create(data);
	}

	@Override
	public List<ComponentTypeArchitectureDTO> componentTypeArchitectureMetrics(String componentType,
			String architecture, List<MetricPropertiesDTO> metricProperties) {
		return componentTypeArchitectureService.componentTypeArchitectureMetrics(componentType, architecture,
				metricProperties);
	}

	@Override
	public List<MetricValuesOutDTO> addMetricValues(String componentType, String architecture, String metricName,
			AnalysisType metricType, MetricValuesDTO values) {
		return componentTypeArchitectureService.defineMetricValues(componentType, architecture, metricName, metricType,
				values);
	}
}
