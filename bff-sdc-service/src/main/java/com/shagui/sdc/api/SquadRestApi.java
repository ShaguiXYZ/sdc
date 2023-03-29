package com.shagui.sdc.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.ComponentView;
import com.shagui.sdc.api.view.SquadView;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface SquadRestApi {

	@Operation(summary = "Retrieve squad by id")
	@GetMapping("squad/{squadId}")
	SquadView squad(@PathVariable @Parameter(description = "Squad identifier") int squadId);

	@Operation(summary = "Retrieve available squads")
	@GetMapping("squads")
	PageData<SquadView> squads(
			@RequestParam(name = "page", required = false) @Parameter(description = "Page number") Integer page);

	@Operation(summary = "Retrieve available squads")
	@GetMapping("squads/{departmentId}")
	PageData<SquadView> squads(@PathVariable @Parameter(description = "Department identifier") int departmentId,
			@RequestParam(name = "page", required = false) @Parameter(description = "Page number") Integer page);

	@Operation(summary = "Retrieve squad components")
	@GetMapping("squad/{squadId}/components")
	PageData<ComponentView> squadComponents(@PathVariable @Parameter(description = "Squad identifier") int squadId,
			@RequestParam(name = "page", required = false) @Parameter(description = "Page number") Integer page);
}
