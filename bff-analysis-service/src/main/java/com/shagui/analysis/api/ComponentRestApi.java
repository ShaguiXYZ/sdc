package com.shagui.analysis.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shagui.analysis.api.view.ComponentHistoricalCoverageView;
import com.shagui.analysis.api.view.PageableView;

import feign.Headers;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/component", "/api/components" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface ComponentRestApi {
	@GetMapping("{componentId}/historicalCoverage")
	PageableView<ComponentHistoricalCoverageView> historicalCoverage(int componentId);
}
