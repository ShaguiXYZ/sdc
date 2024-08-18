package com.shagui.sdc.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.SquadDTO;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface SquadRestApi {
	@Operation(summary = "Retrieve squad by id")
	@GetMapping("public/squad/{squadId}")
	SquadDTO squad(@PathVariable @Parameter(description = "Squad identifier") int squadId);

	@Operation(summary = "Retrieve available squads")
	@GetMapping("public/squads")
	PageData<SquadDTO> squads(
			@RequestParam(required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(required = false) @Parameter(description = "Page size") Integer ps);

	@Operation(summary = "Retrieve available squads by department")
	@GetMapping("public/squads/department/{departmentId}")
	PageData<SquadDTO> squadsByDepartment(
			@PathVariable @Parameter(description = "Department identifier") int departmentId,
			@RequestParam(required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(required = false) @Parameter(description = "Page size") Integer ps);

	@Operation(summary = "Retrieve available squads by company")
	@GetMapping("public/squads/company/{companyId}")
	PageData<SquadDTO> squadsByCompany(
			@PathVariable @Parameter(description = "Company identifier") int companyId,
			@RequestParam(required = false) @Parameter(description = "Page number") Integer page,
			@RequestParam(required = false) @Parameter(description = "Page size") Integer ps);

	@Operation(summary = "Retrieve squads with coverage")
	@GetMapping("public/squads/coverage")
	Long countWithCoverage();
}
