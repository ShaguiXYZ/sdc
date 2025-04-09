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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface AnalysisRestApi {
	@Operation(summary = "Retrieve last component analysis", description = "Fetches the latest analysis for a given component ID.")
	@GetMapping("public/analysis/get/{componentId}")
	PageData<MetricAnalysisDTO> analysis(
			@PathVariable @Parameter(description = "Component identifier") int componentId);

	@Operation(summary = "Retrieve last component analysis metric", description = "Fetches the latest analysis for a specific metric of a component.")
	@GetMapping("public/analysis/get/{componentId}/{metricId}")
	MetricAnalysisDTO analysis(@PathVariable @Parameter(description = "Component identifier") int componentId,
			@PathVariable @Parameter(description = "Metric identifier") int metricId);

	@Operation(summary = "Retrieve component metric history")
	@GetMapping("public/analysis/{componentId}/{metricId}")
	PageData<MetricAnalysisDTO> metricHistory(
			@PathVariable @Parameter(description = "Component identifier") int componentId,
			@PathVariable @Parameter(description = "Metric identifier") int metricId,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date from,
			@RequestParam(required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(required = false) @Parameter(description = "Page size") Integer ps);

	@Operation(summary = "Retrieve component metric history")
	@GetMapping("public/analysis/{componentId}/{metricName}/{type}")
	PageData<MetricAnalysisDTO> metricHistory(
			@PathVariable @Parameter(description = "Component identifier") int componentId,
			@PathVariable @Parameter(description = "Metric name") String metricName,
			@PathVariable @Parameter(description = "Metric type") AnalysisType type,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date from,
			@RequestParam(required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(required = false) @Parameter(description = "Page size") Integer ps);

	@Operation(summary = "Retrieve annual summary of a NUMERIC_MAP metric")
	@GetMapping("public/analysis/annualSum")
	PageData<MetricAnalysisDTO> annualSum(@RequestParam(required = true) String metricName,
			@RequestParam(required = true) AnalysisType metricType,
			@RequestParam(required = false) Integer componentId,
			@RequestParam(required = false) Integer squadId, @RequestParam(required = false) Integer departmentId);

	@Operation(summary = "Run analysis for a component name of a squad")
	@PostMapping("analysis/{squadId}/{componentName}")
	@ResponseStatus(HttpStatus.CREATED)
	PageData<MetricAnalysisDTO> analyze(@PathVariable @Parameter(description = "squad id") int squadId,
			@PathVariable @Parameter(description = "component name") String componentName);

	@Operation(summary = "Run analysis for a component")
	@PostMapping("analysis/{componentId}")
	@ResponseStatus(HttpStatus.CREATED)
	PageData<MetricAnalysisDTO> analyze(@PathVariable @Parameter(description = "component identifier") int componentId);
}
