package com.shagui.analysis.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.analysis.api.view.ComponentView;
import com.shagui.analysis.api.view.PageableView;
import com.shagui.analysis.api.view.SquadView;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/squad", "/api/squads" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface SquadRestApi {
	@Operation(summary = "Retrieve available squads")
	@GetMapping
	PageableView<SquadView> squads(@RequestParam(name = "page", required = false) @Parameter(description = "Page number") Integer page);

	@Operation(summary = "Retrieve squad components")
	@GetMapping("{sqadId}/components")
	PageableView<ComponentView> squadComponents(@PathVariable @Parameter(description = "Squad identifier") int sqadId,
			@RequestParam(name = "page", required = false) @Parameter(description = "Page number") Integer page);
}
