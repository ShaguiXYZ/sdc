package com.shagui.sdc.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentTypeArchitectureDTO;
import com.shagui.sdc.api.dto.MetricPropertiesDTO;
import com.shagui.sdc.api.dto.MetricValuesDTO;
import com.shagui.sdc.api.dto.MetricValuesOutDTO;
import com.shagui.sdc.enums.AnalysisType;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface ComponentTypeArchitectureApi {
	@Operation(summary = "Retrieve component type - architecture list")
	@GetMapping("public/componentTypeArchitecture")
	PageData<ComponentTypeArchitectureDTO> componentTypeArchitecture(
			@RequestParam(required = false) @Parameter(description = "component type") String componentType,
			@RequestParam(required = false) @Parameter(description = "architecture") String architecture,
			@RequestParam(required = false) @Parameter(description = "network") String network,
			@RequestParam(required = false) @Parameter(description = "deployment type") String deploymentType,
			@RequestParam(required = false) @Parameter(description = "platform") String platform,
			@RequestParam(required = false) @Parameter(description = "language") String language);

	@Operation(summary = "Update Component Type - Architecture")
	@PatchMapping("componentTypeArchitecture/{componentTypeArchitectureId}")
	@ResponseStatus(HttpStatus.OK)
	ComponentTypeArchitectureDTO update(
			@PathVariable @Parameter(description = "component type - architecture identifier") int componentTypeArchitectureId,
			@RequestBody ComponentTypeArchitectureDTO data);

	@Operation(summary = "Create new elements of Component Type - Architecture")
	@PostMapping(value = "componentTypeArchitecture", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	List<ComponentTypeArchitectureDTO> create(@RequestBody List<ComponentTypeArchitectureDTO> data);

	@Operation(summary = "Associate metrics with a type of component and/or architecture")
	@PostMapping("componentTypeArchitecture/metricsByArchitecture")
	@ResponseStatus(HttpStatus.CREATED)
	List<ComponentTypeArchitectureDTO> componentTypeArchitectureMetrics(
			@RequestParam(required = false) @Parameter(description = "component type") String componentType,
			@RequestParam(required = false) @Parameter(description = "architecture name") String architecture,
			@RequestBody List<MetricPropertiesDTO> metricProperties);

	@Operation(summary = "Create new ranges for a metric and a type of component and/or architecture")
	@PostMapping("componentTypeArchitecture/metricValues")
	@ResponseStatus(HttpStatus.CREATED)
	List<MetricValuesOutDTO> addMetricValues(
			@RequestParam(required = false) @Parameter(description = "component type") String componentType,
			@RequestParam(required = false) @Parameter(description = "architecture name") String architecture,
			@RequestParam(required = true) @Parameter(description = "metric name") String metricName,
			@RequestParam(required = true) @Parameter(description = "metric type") AnalysisType metricType,
			@RequestBody MetricValuesDTO values);
}
