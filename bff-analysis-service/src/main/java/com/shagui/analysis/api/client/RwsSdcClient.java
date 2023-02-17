package com.shagui.analysis.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.MetricAnalysisDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@FeignClient(name = "security-service", url = "${services.rws-sdc}", primary = false)
public interface RwsSdcClient {
	@GetMapping("analysis/{componentId}/{metricId}")
	List<MetricAnalysisDTO> metricHistory(
			@PathVariable @Parameter(description = "Component identifier") int componentId,
			@PathVariable @Parameter(description = "Metric identifier") int metricId);

	@GetMapping("analysis/{componentId}")
	List<MetricAnalysisDTO> componentState(
			@PathVariable @Parameter(description = "Component identifier") int componentId);
	
	@Operation(summary = "Retrieve squad components")
	@GetMapping("squads/{sqadId}/components")
	List<ComponentDTO> squadComponents(@PathVariable @Parameter(description = "Squad identifier") int sqadId);
}