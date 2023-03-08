package com.shagui.analysis.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.api.dto.SquadDTO;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = "/api/sonar", produces = { MediaType.APPLICATION_JSON_VALUE })
public interface SonarApi {
	@Operation(summary = "Retrieve available squads")
	@GetMapping("{uriId}/components")
	PageableDTO<SquadDTO> components(
			@PathVariable(value = "uriId") @Parameter(description = "uri identifier") int uriId,
			@RequestParam(name = "page", required = false) @Parameter(description = "Page number") Integer page);
}
