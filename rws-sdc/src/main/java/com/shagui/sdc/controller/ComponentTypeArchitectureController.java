package com.shagui.sdc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.ComponentTypeArchitectureApi;
import com.shagui.sdc.api.dto.ComponentTypeArchitectureDTO;
import com.shagui.sdc.api.dto.MetricPropertiesDTO;
import com.shagui.sdc.api.dto.MetricValuesDTO;
import com.shagui.sdc.api.dto.MetricValuesOutDTO;
import com.shagui.sdc.service.ComponentTypeArchitectureService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "component-type-architecture", description = "API to maintain Components type architectures")
public class ComponentTypeArchitectureController implements ComponentTypeArchitectureApi {

	@Autowired
	private ComponentTypeArchitectureService componentTypeArchitectureService;

	@Override
	public List<ComponentTypeArchitectureDTO> architectureMetrics(String componentType, String architecture,
			Map<String, MetricPropertiesDTO> metricProperties) {
		return componentTypeArchitectureService.componentTypeMetrics(componentType, architecture, metricProperties);
	}

	@Override
	public List<MetricValuesOutDTO> addMetricValues(String componentType, String architecture, String metric,
			MetricValuesDTO values) {
		return componentTypeArchitectureService.defineMetricValues(componentType, architecture, metric, values);
	}
}
