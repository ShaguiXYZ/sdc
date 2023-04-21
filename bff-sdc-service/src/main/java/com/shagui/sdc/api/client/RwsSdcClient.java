package com.shagui.sdc.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shagui.sdc.api.domain.HistoricalCoverage;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.api.dto.SquadDTO;

@FeignClient(name = "rws-sdc-service", url = "${services.rws-sdc}", primary = false)
public interface RwsSdcClient {
	@GetMapping("analysis/get/{componentId}/{metricId}")
	MetricAnalysisDTO analysis(@PathVariable int componentId, @PathVariable int metricId);

	@GetMapping("analysis/{componentId}/{metricId}")
	PageData<MetricAnalysisDTO> metricHistory(@PathVariable int componentId, @PathVariable int metricId);

	@PostMapping("analysis/{componentId}")
	@ResponseStatus(HttpStatus.CREATED)
	PageData<MetricAnalysisDTO> analyze(@PathVariable int componentId);

	@GetMapping("squad/{squadId}")
	SquadDTO squad(@PathVariable int squadId);

	@GetMapping("squads")
	PageData<SquadDTO> squads(@RequestParam(name = "page", required = false) Integer page);

	@GetMapping("squads/{departmentId}")
	PageData<SquadDTO> squadsByDepartment(@PathVariable int departmentId,
			@RequestParam(name = "page", required = false) Integer page);

	@GetMapping("component/{componentId}")
	ComponentDTO component(@PathVariable(value = "componentId") int componentId);

	@GetMapping("components/filter")
	PageData<ComponentDTO> filter(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "squadId", required = false) Integer squadId,
			@RequestParam(name = "coverageMin", required = false) Float coverageMin,
			@RequestParam(name = "coverageMax", required = false) Float coverageMax,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "ps", required = false) Integer ps);

	@GetMapping("component/{componentId}/metrics")
	PageData<MetricDTO> componentMetrics(@PathVariable(value = "componentId") int componentId);

	@GetMapping("component/historical/{componentId}")
	HistoricalCoverage<ComponentDTO> componentHistoricalCoverage(@PathVariable int componentId,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "ps", required = false) Integer ps);

}