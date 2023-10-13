package com.shagui.sdc.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shagui.sdc.api.dto.ComponentTypeArchitectureDTO;
import com.shagui.sdc.api.dto.MetricPropertiesDTO;
import com.shagui.sdc.api.dto.MetricValuesDTO;
import com.shagui.sdc.api.dto.MetricValuesOutDTO;
import com.shagui.sdc.enums.AnalysisType;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/componentTypeArchitecture" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface ComponentTypeArchitectureApi {
	@Operation(summary = "Retrieve component type - architecture list")
	@GetMapping
	List<ComponentTypeArchitectureDTO> componentTypeArchitecture(
			@RequestParam(required = false) @Parameter(description = "component type") String componentType,
			@RequestParam(required = false) @Parameter(description = "architecture") String architecture,
			@RequestParam(required = false) @Parameter(description = "network") String network,
			@RequestParam(required = false) @Parameter(description = "deployment type") String deploymentType,
			@RequestParam(required = false) @Parameter(description = "platform") String platform,
			@RequestParam(required = false) @Parameter(description = "language") String language);

	@Operation(summary = "Update Component Type - Architecture")
	@PutMapping("{componentId}")
	@ResponseStatus(HttpStatus.OK)
	ComponentTypeArchitectureDTO update(
			@PathVariable @Parameter(description = "component type - architecture identifier") int componentTypeArchitectureId,
			@RequestBody ComponentTypeArchitectureDTO data);

	@Operation(summary = "Create new elements of Component Type - Architecture")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	List<ComponentTypeArchitectureDTO> create(@RequestBody List<ComponentTypeArchitectureDTO> data);

	@PostMapping("metricsByArchitecture")
	List<ComponentTypeArchitectureDTO> architectureMetrics(
			@RequestParam(required = false) @Parameter(description = "component type") String componentType,
			@RequestParam(required = false) @Parameter(description = "architecture name") String architecture,
			@RequestBody List<MetricPropertiesDTO> metricProperties);

	@PostMapping("metricValues")
	List<MetricValuesOutDTO> addMetricValues(
			@RequestParam(required = false) @Parameter(description = "component type") String componentType,
			@RequestParam(required = false) @Parameter(description = "architecture name") String architecture,
			@RequestParam(required = true) @Parameter(description = "metric name") String metricName,
			@RequestParam(required = true) @Parameter(description = "metric type") AnalysisType metricType,
			@RequestBody MetricValuesDTO values);
}
