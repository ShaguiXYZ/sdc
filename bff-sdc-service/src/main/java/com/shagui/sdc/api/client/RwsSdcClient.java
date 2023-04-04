package com.shagui.sdc.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.api.dto.SquadDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@FeignClient(name = "rws-sdc-service", url = "${services.rws-sdc}", primary = false)
public interface RwsSdcClient {
	@GetMapping("analysis/{componentId}/{metricId}")
	PageData<MetricAnalysisDTO> metricHistory(
			@PathVariable @Parameter(description = "Component identifier") int componentId,
			@PathVariable @Parameter(description = "Metric identifier") int metricId);

	@Operation(summary = "Retrieve squad by id")
	@GetMapping("squad/{squadId}")
	SquadDTO squad(@PathVariable @Parameter(description = "Squad identifier") int squadId);

	@Operation(summary = "Retrieve available squads")
	@GetMapping("squads")
	PageData<SquadDTO> squads(
			@RequestParam(name = "page", required = false) @Parameter(description = "Page number") Integer page);

	@Operation(summary = "Retrieve available squads")
	@GetMapping("squads/{departmentId}")
	PageData<SquadDTO> squadsByDepartment(
			@PathVariable @Parameter(description = "Department identifier") int departmentId,
			@RequestParam(name = "page", required = false) @Parameter(description = "Page number") Integer page);

	@GetMapping("components/filter")
	PageData<ComponentDTO> filter(
			@RequestParam(name = "name", required = false) @Parameter(description = "Component name") String name,
			@RequestParam(name = "squadId", required = false) @Parameter(description = "Squad identifier") Integer squadId,
			@RequestParam(name = "page", required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(name = "ps", required = false) @Parameter(description = "Page size") Integer ps);

	@Operation(summary = "Retrieve squad components")
	@GetMapping("component/{componentId}/metrics")
	PageData<MetricDTO> componentMetrics(
			@PathVariable(value = "componentId") @Parameter(description = "component identifier") int componentId);
}