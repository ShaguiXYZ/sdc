package com.shagui.sdc.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.MetricAnalysisView;

import feign.Headers;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = "/api/analysis", produces = { MediaType.APPLICATION_JSON_VALUE })
public interface AnalysisRestApi {
	@GetMapping("{componentId}/{metricId}")
	PageData<MetricAnalysisView> metricHistory(
			@PathVariable @Parameter(description = "Component identifier") int componentId,
			@PathVariable @Parameter(description = "Metric identifier") int metricId);
}
