package com.shagui.sdc.api;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.dto.ComponentTypeArchitectureDTO;
import com.shagui.sdc.api.dto.MetricPropertiesDTO;
import com.shagui.sdc.api.dto.MetricValuesDTO;
import com.shagui.sdc.api.dto.MetricValuesOutDTO;

import feign.Headers;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/componentTypeArchitecture" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface ComponentTypeArchitectureApi {
	@PostMapping("metricsByArchitecture")
	List<ComponentTypeArchitectureDTO> architectureMetrics(
			@RequestParam(required = false) @Parameter(description = "component type") String componentType,
			@RequestParam(required = false) @Parameter(description = "architecture name") String architecture,
			@RequestBody Map<String, MetricPropertiesDTO> metricProperties);

	@PostMapping("metricValues")
	List<MetricValuesOutDTO> addMetricValues(
			@RequestParam(required = false) @Parameter(description = "component type") String componentType,
			@RequestParam(required = false) @Parameter(description = "architecture name") String architecture,
			@RequestParam(required = true) @Parameter(description = "metric name") String metric,
			@RequestBody MetricValuesDTO values);
}
