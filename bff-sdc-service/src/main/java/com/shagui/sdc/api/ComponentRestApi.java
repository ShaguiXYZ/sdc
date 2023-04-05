package com.shagui.sdc.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.ComponentView;
import com.shagui.sdc.api.view.MetricView;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface ComponentRestApi {
	@Operation(summary = "Retrieve component metrics")
	@GetMapping("component/{componentId}/metrics")
	PageData<MetricView> componentMetrics(
			@PathVariable(value = "componentId") @Parameter(description = "component identifier") int componentId);

	@GetMapping("components/filter")
	PageData<ComponentView> filter(
			@RequestParam(name = "name", required = false) @Parameter(description = "Component name") String name,
			@RequestParam(name = "squadId", required = false) @Parameter(description = "Squad identifier") Integer squadId,
			@RequestParam(name = "coverageMin", required = false) @Parameter(description = "Component coverage min range") Float coverageMin,
			@RequestParam(name = "coverageMax", required = false) @Parameter(description = "Component coverage max range") Float coverageMax,
			@RequestParam(name = "page", required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(name = "ps", required = false) @Parameter(description = "Page size") Integer ps);
}
