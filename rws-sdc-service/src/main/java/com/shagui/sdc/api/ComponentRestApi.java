package com.shagui.sdc.api;

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

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricDTO;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/component", "/api/components" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface ComponentRestApi {
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

	@GetMapping("filter")
	PageData<ComponentDTO> filter(
			@RequestParam(name = "name", required = false) @Parameter(description = "Component name") String name,
			@RequestParam(name = "squadId", required = false) @Parameter(description = "Squad identifier") Integer squadId,
			@RequestParam(name = "page", required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(name = "ps", required = false) @Parameter(description = "Page size") Integer ps);

	@Operation(summary = "Retrieve squad components")
	@GetMapping("{componentId}/metrics")
	PageData<MetricDTO> componentMetrics(
			@PathVariable(value = "componentId") @Parameter(description = "component identifier") int componentId);
}
