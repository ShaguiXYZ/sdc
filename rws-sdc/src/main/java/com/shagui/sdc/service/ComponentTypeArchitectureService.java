package com.shagui.sdc.service;

import java.util.List;
import java.util.Map;

import com.shagui.sdc.api.dto.ComponentTypeArchitectureDTO;
import com.shagui.sdc.api.dto.MetricPropertiesDTO;

public interface ComponentTypeArchitectureService {
	List<ComponentTypeArchitectureDTO> componentTypeMetrics(String componentType, String architecture,
			Map<String, MetricPropertiesDTO> metricProperties);
}
