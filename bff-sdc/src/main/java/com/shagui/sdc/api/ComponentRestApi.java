package com.shagui.sdc.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.domain.HistoricalCoverage;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.ComponentView;
import com.shagui.sdc.api.view.MetricView;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface ComponentRestApi {
	@Operation(summary = "Retrieve component by Id")
	@GetMapping("component/{componentId}")
	ComponentView component(
			@PathVariable @Parameter(description = "component identifier") int componentId);

	@Operation(summary = "Retrieve component metrics")
	@GetMapping("component/{componentId}/metrics")
	PageData<MetricView> componentMetrics(
			@PathVariable @Parameter(description = "component identifier") int componentId);

	@GetMapping("components/squad/{squadId}")
	PageData<ComponentView> squadComponents(
			@PathVariable @Parameter(description = "Squad identifier") int squadId,
			@RequestParam(required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(required = false) @Parameter(description = "Page size") Integer ps);

	@GetMapping("components/filter")
	PageData<ComponentView> filter(
			@RequestParam(required = false) @Parameter(description = "Component name") String name,
			@RequestParam(required = false) @Parameter(description = "Squad identifier") Integer squadId,
			@RequestParam(required = false) @Parameter(description = "Component coverage min range") Float coverageMin,
			@RequestParam(required = false) @Parameter(description = "Component coverage max range") Float coverageMax,
			@RequestParam(required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(required = false) @Parameter(description = "Page size") Integer ps);

	@GetMapping("component/historical/{componentId}")
	HistoricalCoverage<ComponentView> historical(
			@PathVariable @Parameter(description = "component identifier") int componentId,
			@RequestParam(required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(required = false) @Parameter(description = "Page size") Integer ps);
}
