package com.shagui.sdc.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.HistoricalCoverageDTO;

import feign.Headers;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/component/historical" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface ComponentHistoricalCoverageApi {
	@GetMapping("{componentId}")
	public HistoricalCoverageDTO<ComponentDTO> historicalCoverage(
			@PathVariable @Parameter(description = "Component identifier") int componentId,
			@RequestParam(name = "page", required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(name = "ps", required = false) @Parameter(description = "Page size") Integer ps);
}
