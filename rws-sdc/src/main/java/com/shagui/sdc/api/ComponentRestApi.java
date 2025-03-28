package com.shagui.sdc.api;

import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.api.dto.ebs.ComponentInput;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface ComponentRestApi {
	@Operation(summary = "Retrieve component by Id")
	@GetMapping("public/component/{componentId}")
	ComponentDTO component(@PathVariable @Parameter(description = "component identifier") int componentId);

	@Operation(summary = "Retrieve squad components")
	@GetMapping("public/components/squad/{squadId}")
	PageData<ComponentDTO> squadComponents(@PathVariable @Parameter(description = "Squad identifier") int squadId,
			@RequestParam(required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(required = false) @Parameter(description = "Page size") Integer ps);

	@GetMapping("public/components/filter")
	PageData<ComponentDTO> filter(
			@RequestParam(required = false) @Parameter(description = "Component name") String name,
			@RequestParam(required = false) @Parameter(description = "Squad identifier") Integer squadId,
			@RequestParam(required = false) @Parameter(description = "Component tags") Set<String> tags,
			@RequestParam(required = false) @Parameter(description = "Component coverage min range") Float coverageMin,
			@RequestParam(required = false) @Parameter(description = "Component coverage max range") Float coverageMax,
			@RequestParam(required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(required = false) @Parameter(description = "Page size") Integer ps);

	@Operation(summary = "Retrieve component metrics")
	@GetMapping("public/component/{componentId}/metrics")
	PageData<MetricDTO> componentMetrics(
			@PathVariable @Parameter(description = "component identifier") int componentId);

	@Operation(summary = "Retrieve component dictionary")
	@GetMapping("public/component/{componentId}/dictionary")
	Map<String, String> dictionary(
			@PathVariable @Parameter(description = "component identifier") int componentId);

	@Operation(summary = "Create new component")
	@PatchMapping("component")
	@ResponseStatus(HttpStatus.CREATED)
	ComponentDTO patch(@RequestBody ComponentInput data);
}
