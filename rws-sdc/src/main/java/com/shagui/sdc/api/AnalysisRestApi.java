package com.shagui.sdc.api;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.enums.AnalysisType;

import feign.Headers;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/analysis" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface AnalysisRestApi {
	@GetMapping("get/{componentId}")
	PageData<MetricAnalysisDTO> analysis(
			@PathVariable @Parameter(description = "Component identifier") int componentId);

	@GetMapping("get/{componentId}/{metricId}")
	MetricAnalysisDTO analysis(@PathVariable @Parameter(description = "Component identifier") int componentId,
			@PathVariable @Parameter(description = "Metric identifier") int metricId);

	@PostMapping("{squadId}/{componentName}")
	@ResponseStatus(HttpStatus.CREATED)
	PageData<MetricAnalysisDTO> analyze(@PathVariable @Parameter(description = "squad id") int squadId,
			@PathVariable @Parameter(description = "component name") String componentName);

	@PostMapping("{componentId}")
	@ResponseStatus(HttpStatus.CREATED)
	PageData<MetricAnalysisDTO> analyze(@PathVariable @Parameter(description = "component identifier") int componentId);

	@GetMapping("{componentId}/{metricId}")
	PageData<MetricAnalysisDTO> metricHistory(
			@PathVariable @Parameter(description = "Component identifier") int componentId,
			@PathVariable @Parameter(description = "Metric identifier") int metricId,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date from);

	@GetMapping("{componentId}/{metricName}/{type}")
	PageData<MetricAnalysisDTO> metricHistory(
			@PathVariable @Parameter(description = "Component identifier") int componentId,
			@PathVariable @Parameter(description = "Metric name") String metricName,
			@PathVariable @Parameter(description = "Metric type") AnalysisType type,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date from);
}
