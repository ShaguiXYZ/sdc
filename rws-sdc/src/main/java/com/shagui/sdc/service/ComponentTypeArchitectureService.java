package com.shagui.sdc.service;

import java.util.List;
import java.util.Map;

import com.shagui.sdc.api.dto.ComponentTypeArchitectureDTO;
import com.shagui.sdc.api.dto.MetricPropertiesDTO;
import com.shagui.sdc.api.dto.MetricValuesDTO;
import com.shagui.sdc.api.dto.MetricValuesOutDTO;

public interface ComponentTypeArchitectureService {
	List<ComponentTypeArchitectureDTO> componentTypeMetrics(String componentType, String architecture,
			Map<String, MetricPropertiesDTO> metricProperties);

	List<MetricValuesOutDTO> defineMetricValues(String componentType, String architecture, String metricName,
			MetricValuesDTO values);
}
