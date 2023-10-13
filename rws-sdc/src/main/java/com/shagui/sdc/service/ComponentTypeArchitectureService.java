package com.shagui.sdc.service;

import java.util.List;

import com.shagui.sdc.api.dto.ComponentTypeArchitectureDTO;
import com.shagui.sdc.api.dto.MetricPropertiesDTO;
import com.shagui.sdc.api.dto.MetricValuesDTO;
import com.shagui.sdc.api.dto.MetricValuesOutDTO;
import com.shagui.sdc.enums.AnalysisType;

public interface ComponentTypeArchitectureService {
	List<ComponentTypeArchitectureDTO> findBy(String componentType, String architecture, String network,
			String deploymentType, String platform, String language);

	ComponentTypeArchitectureDTO update(int componentTypeArchitectureId, ComponentTypeArchitectureDTO data);

	List<ComponentTypeArchitectureDTO> create(List<ComponentTypeArchitectureDTO> data);

	List<ComponentTypeArchitectureDTO> componentTypeArchitectureMetrics(String componentType, String architecture,
			List<MetricPropertiesDTO> metricProperties);

	List<MetricValuesOutDTO> defineMetricValues(String componentType, String architecture, String metricName,
			AnalysisType metricType, MetricValuesDTO values);
}
