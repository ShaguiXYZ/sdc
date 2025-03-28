package com.shagui.sdc.api;

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

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface MetricRestApi {
	@Operation(summary = "Retrieve metric by Id")
	@GetMapping({ "public/metric/{metricId}" })
	MetricDTO metric(@PathVariable @Parameter(description = "metric identifier") int metricId);

	@Operation(summary = "Retrieve all available metrics")
	@GetMapping("public/metrics")
	PageData<MetricDTO> metrics();

	@Operation(summary = "Create new metric")
	@PostMapping("metric")
	@ResponseStatus(HttpStatus.CREATED)
	MetricDTO create(@RequestBody MetricDTO metric);

	@Operation(summary = "Create new metric")
	@PostMapping("metric/values")
	@ResponseStatus(HttpStatus.CREATED)
	MetricDTO createValues(@RequestParam String name, @RequestParam(required = false) String description,
			@RequestParam(required = false) AnalysisType type, @RequestParam MetricValueType valueType,
			@RequestParam(required = false) MetricValidation validation, @RequestParam String value,
			@RequestParam(required = false, defaultValue = "false") boolean blocker);

	@Operation(summary = "Update an specific Metric", description = "Field metricId should match the metricId from url")
	@PutMapping("metric/{metricId}")
	@ResponseStatus(HttpStatus.OK)
	MetricDTO update(@PathVariable @Parameter(description = "metric identifier") int metricId,
			@RequestBody MetricDTO metric);
}
