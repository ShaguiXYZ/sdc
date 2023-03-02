package com.shagui.analysis.api;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.MetricAnalysisStateDTO;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/component", "/api/components" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface ComponentRestApi {
	@GetMapping("{componentId}/state")
	MetricAnalysisStateDTO componentState(@PathVariable @Parameter(description = "Component identifier") int componentId,
			@RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date date);

	@Operation(summary = "Create new component")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	ComponentDTO create(@RequestBody ComponentDTO component);

	@Operation(summary = "Update an specific Component", description = "Field componentId should match the componentId from url")
	@PutMapping("{componentId}")
	@ResponseStatus(HttpStatus.OK)
	ComponentDTO update(
			@PathVariable(value = "componentId") @Parameter(description = "component identifier") int componentId,
			@RequestBody ComponentDTO component);
}
