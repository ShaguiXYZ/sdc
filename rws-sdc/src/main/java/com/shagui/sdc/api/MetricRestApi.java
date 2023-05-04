package com.shagui.sdc.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shagui.sdc.api.dto.MetricDTO;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/metric", "/api/metrics" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface MetricRestApi {
	@Operation(summary = "Create new metric")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	MetricDTO create(@RequestBody MetricDTO metric);

	@Operation(summary = "Update an specific Metric", description = "Field metricId should match the metricId from url")
	@PutMapping("{metricId}")
	@ResponseStatus(HttpStatus.OK)
	MetricDTO update(@PathVariable(value = "metricId") @Parameter(description = "metric identifier") int metricId,
			@RequestBody MetricDTO metric);
}
