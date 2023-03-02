package com.shagui.analysis.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shagui.analysis.api.view.MetricAnalysisView;
import com.shagui.analysis.api.view.PageableView;

import feign.Headers;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/analysis" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface AnalysisRestApi {
	@GetMapping("{componentId}/{metricId}")
	PageableView<MetricAnalysisView> metricHistory(
			@PathVariable @Parameter(description = "Component identifier") int componentId,
			@PathVariable @Parameter(description = "Metric identifier") int metricId);
}
