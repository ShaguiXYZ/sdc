package com.shagui.sdc.api;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.dto.ComponentTypeArchitectureDTO;
import com.shagui.sdc.api.dto.MetricPropertiesDTO;

import feign.Headers;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/componentTypeArchitecture" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface ComponentTypeArchitectureApi {
	@PatchMapping("metricsByArchitecture")
	List<ComponentTypeArchitectureDTO> architectureMetrics(
			@RequestParam(required = false) @Parameter(description = "component type") String componentType,
			@RequestParam(required = false) @Parameter(description = "architecture name") String architecture,
			@RequestBody Map<String, MetricPropertiesDTO> metricProperties);
}
