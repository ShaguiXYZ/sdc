package com.shagui.analysis.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.ComponentStateDTO;
import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.api.dto.SquadDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@FeignClient(name = "security-service", url = "${services.rws-sdc}", primary = false)
public interface RwsSdcClient {
	@GetMapping("analysis/{componentId}/{metricId}")
	PageableDTO<MetricAnalysisDTO> metricHistory(
			@PathVariable @Parameter(description = "Component identifier") int componentId,
			@PathVariable @Parameter(description = "Metric identifier") int metricId);

	@GetMapping("analysis/{componentId}")
	ComponentStateDTO componentState(
			@PathVariable @Parameter(description = "Component identifier") int componentId);
	
	@Operation(summary = "Retrieve available squads")
	@GetMapping("squads")
	PageableDTO<SquadDTO> squads(@RequestParam(name = "page", required = false) @Parameter(description = "Page number") Integer page);
	
	@Operation(summary = "Retrieve squad components")
	@GetMapping("squad/{sqadId}/components")
	PageableDTO<ComponentDTO> squadComponents(@PathVariable @Parameter(description = "Squad identifier") int sqadId,
			@RequestParam(name = "page", required = false) @Parameter(description = "Page number") Integer page);
}