package com.shagui.sdc.api.client;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.config.SdcConfig;
import com.shagui.sdc.api.domain.HistoricalCoverage;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.ComponentTagDTO;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.api.dto.SummaryViewDTO;
import com.shagui.sdc.api.dto.TagDTO;
import com.shagui.sdc.api.dto.UriDTO;

import io.swagger.v3.oas.annotations.Parameter;

@FeignClient(name = "rws-sdc", url = "${services.rws-sdc}", primary = false)
public interface RwsSdcClient {
	@GetMapping("public/analysis/get/{componentId}")
	PageData<MetricAnalysisDTO> analysis(@PathVariable int componentId);

	@GetMapping("public/analysis/get/{componentId}/{metricId}")
	MetricAnalysisDTO analysis(@PathVariable int componentId, @PathVariable int metricId);

	@GetMapping("public/analysis/{componentId}/{metricId}")
	PageData<MetricAnalysisDTO> metricHistory(
			@PathVariable int componentId, @PathVariable int metricId,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer ps);

	@GetMapping("public/analysis/{componentId}/{metricName}/{type}")
	PageData<MetricAnalysisDTO> metricHistory(
			@PathVariable int componentId,
			@PathVariable String metricName,
			@PathVariable String type,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer ps);

	@GetMapping("public/analysis/annualSum")
	PageData<MetricAnalysisDTO> annualSum(
			@RequestParam(required = true) String metricName,
			@RequestParam(required = true) String metricType,
			@RequestParam(required = false) Integer componentId,
			@RequestParam(required = false) Integer squadId,
			@RequestParam(required = false) Integer departmentId);

	@PostMapping(value = "analysis/{componentId}")
	PageData<MetricAnalysisDTO> analyze(@PathVariable int componentId);

	@GetMapping("public/component/{componentId}")
	ComponentDTO component(@PathVariable int componentId);

	@GetMapping("public/component/{componentId}/metrics")
	PageData<MetricDTO> componentMetrics(@PathVariable int componentId);

	@GetMapping("public/component/historical/{componentId}")
	HistoricalCoverage<ComponentDTO> componentHistoricalCoverage(@PathVariable int componentId,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer ps);

	@GetMapping("public/configurations")
	SdcConfig configurations();

	@GetMapping("public/datalist/availables")
	List<String> dataLists();

	@GetMapping("public/datalist/{datalist}")
	List<String> dataListValues(@PathVariable String datalist);

	@GetMapping("public/components/squad/{squadId}")
	PageData<ComponentDTO> squadComponents(@PathVariable int squadId, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer ps);

	@GetMapping("public/components/filter")
	PageData<ComponentDTO> filter(@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer squadId, @RequestParam(required = false) Set<String> tags,
			@RequestParam(required = false) Float coverageMin, @RequestParam(required = false) Float coverageMax,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer ps);

	@GetMapping("public/department/{departmentId}")
	DepartmentDTO department(@PathVariable int departmentId);

	@GetMapping("public/departments")
	PageData<DepartmentDTO> departments(@RequestParam(required = false) Integer page);

	@GetMapping("public/departments/company/{companyId}")
	PageData<DepartmentDTO> departmentsByCompany(@PathVariable int companyId,
			@RequestParam(required = false) Integer page);

	@GetMapping("public/squad/{squadId}")
	SquadDTO squad(@PathVariable int squadId);

	@GetMapping("public/squads")
	PageData<SquadDTO> squads(@RequestParam(required = false) Integer page);

	@GetMapping("public/squads/department/{departmentId}")
	PageData<SquadDTO> squadsByDepartment(@PathVariable int departmentId, @RequestParam(required = false) Integer page);

	@GetMapping("public/squads/company/{companyId}")
	PageData<SquadDTO> squadsByCompany(@PathVariable int companyId, @RequestParam(required = false) Integer page);

	@GetMapping("public/squads/coverage")
	Long countWithCoverage();

	@GetMapping("public/summary/filter")
	PageData<SummaryViewDTO> summaryFilter(
			@RequestParam(required = false) @Parameter(description = "Summary name") String name,
			@RequestParam(required = false) @Parameter(description = "Summary types") Set<String> types,
			@RequestParam(required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(required = false) @Parameter(description = "Page size") Integer ps);

	@GetMapping("public/tags")
	PageData<TagDTO> tags(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer ps);

	@GetMapping("public/tags/component/{componentId}")
	PageData<TagDTO> componentTags(@PathVariable int componentId, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer ps);

	@PostMapping("tag/create/{componentId}/{name}")
	ComponentTagDTO create(@PathVariable int componentId, @PathVariable String name);

	@DeleteMapping("tag/delete/{componentId}/{name}")
	void deleteComponentTag(@PathVariable int componentId, @PathVariable String name);

	@GetMapping("uri/component/{componentId}/type/{type}")
	UriDTO componentUri(@PathVariable(required = true) @Parameter(description = "Component Id") int componentId,
			@PathVariable(required = true) @Parameter(description = "Uri Type") String type);

	@PostMapping("data/jsonDefaultDepartments")
	List<DepartmentDTO> updateDepartments();
}