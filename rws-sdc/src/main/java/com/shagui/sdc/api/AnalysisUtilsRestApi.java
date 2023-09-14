package com.shagui.sdc.api;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import feign.Headers;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/utils" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface AnalysisUtilsRestApi {
	@GetMapping("languageDistribution/{componentId}")
	Map<String, String> languageDistribution(
			@PathVariable @Parameter(description = "Component identifier") int componentId);
}
