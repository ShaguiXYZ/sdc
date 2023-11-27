package com.shagui.sdc.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.domain.HistoricalCoverage;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.ComponentTagDTO;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.api.dto.TagDTO;

@FeignClient(name = "rws-sdc", url = "${services.rws-sdc}", primary = false)
public interface RwsSdcClient {
	@GetMapping("analysis/get/{componentId}")
	PageData<MetricAnalysisDTO> analysis(@PathVariable int componentId);

	@GetMapping("analysis/get/{componentId}/{metricId}")
	MetricAnalysisDTO analysis(@PathVariable int componentId, @PathVariable int metricId);

	@GetMapping("analysis/{componentId}/{metricId}")
	PageData<MetricAnalysisDTO> metricHistory(
			@PathVariable int componentId, @PathVariable int metricId,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer ps);

	@GetMapping("analysis/{componentId}/{metricName}/{type}")
	PageData<MetricAnalysisDTO> metricHistory(
			@PathVariable int componentId,
			@PathVariable String metricName,
			@PathVariable String type,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer ps);

	@GetMapping("analysis/annualSum")
	PageData<MetricAnalysisDTO> annualSum(
			@RequestParam(required = true) String metricName,
			@RequestParam(required = true) String metricType,
			@RequestParam(required = false) Integer componentId,
			@RequestParam(required = false) Integer squadId,
			@RequestParam(required = false) Integer departmentId);

	@PostMapping(value = "analysis/{componentId}")
	PageData<MetricAnalysisDTO> analyze(@PathVariable int componentId);

	@GetMapping("component/{componentId}")
	ComponentDTO component(@PathVariable int componentId);

	@GetMapping("component/{componentId}/metrics")
	PageData<MetricDTO> componentMetrics(@PathVariable int componentId);

	@GetMapping("component/historical/{componentId}")
	HistoricalCoverage<ComponentDTO> componentHistoricalCoverage(@PathVariable int componentId,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer ps);

	@GetMapping("datalist")
	List<String> dataLists();

	@GetMapping("datalist/{datalist}")
	List<String> dataListValues(@PathVariable String datalist);

	@GetMapping("components/squad/{squadId}")
	PageData<ComponentDTO> squadComponents(@PathVariable int squadId, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer ps);

	@GetMapping("components/filter")
	PageData<ComponentDTO> filter(@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer squadId, @RequestParam(required = false) Float coverageMin,
			@RequestParam(required = false) Float coverageMax, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer ps);

	@GetMapping("department/{departmentId}")
	DepartmentDTO department(@PathVariable int departmentId);

	@GetMapping("departments")
	PageData<DepartmentDTO> departments(@RequestParam(required = false) Integer page);

	@GetMapping("squad/{squadId}")
	SquadDTO squad(@PathVariable int squadId);

	@GetMapping("squads")
	PageData<SquadDTO> squads(@RequestParam(required = false) Integer page);

	@GetMapping("squads/{departmentId}")
	PageData<SquadDTO> squadsByDepartment(@PathVariable int departmentId, @RequestParam(required = false) Integer page);

	@GetMapping("tags")
	PageData<TagDTO> tags(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer ps);

	@GetMapping("tags/component/{componentId}")
	PageData<TagDTO> componentTags(@PathVariable int componentId, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer ps);

	@PostMapping("tag/create/{componentId}/{name}")
	ComponentTagDTO create(@PathVariable int componentId, @PathVariable String name);

	@DeleteMapping("tag/delete/{componentId}/{name}")
	void deleteComponentTag(@PathVariable int componentId, @PathVariable String name);
}