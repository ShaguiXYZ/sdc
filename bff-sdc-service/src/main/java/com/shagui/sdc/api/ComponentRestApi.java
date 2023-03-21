package com.shagui.sdc.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shagui.sdc.api.view.ComponentHistoricalCoverageView;
import com.shagui.sdc.api.view.PageableView;

import feign.Headers;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/component", "/api/components" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface ComponentRestApi {
	@GetMapping("{componentId}/historicalCoverage")
	PageableView<ComponentHistoricalCoverageView> historicalCoverage(
			@PathVariable @Parameter(description = "Component identifier") int componentId);
}
