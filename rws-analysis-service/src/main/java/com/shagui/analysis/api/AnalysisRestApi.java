package com.shagui.analysis.api;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shagui.analysis.api.dto.MetricAnalysisDTO;

import feign.Headers;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/analysis" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface AnalysisRestApi {
	@PostMapping("{componentId}")
	@ResponseStatus(HttpStatus.CREATED)
	List<MetricAnalysisDTO> analyzeComponent(
			@PathVariable @Parameter(description = "component identifier") int componentId);

	@GetMapping("{componentId}/{metricId}")
	List<MetricAnalysisDTO> metricHistory(
			@PathVariable @Parameter(description = "Component identifier") int componentId,
			@PathVariable @Parameter(description = "Metric identifier") int metricId,
			@RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date date);

	@GetMapping("{componentId}")
	List<MetricAnalysisDTO> componentState(
			@PathVariable @Parameter(description = "Component identifier") int componentId,
			@RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date date);
}
