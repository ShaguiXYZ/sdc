package com.shagui.sdc.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.model.UriModel;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface UriRestApi {
	@Operation(summary = "Retrieve available URIs", description = "Fetches a list of all available URIs.")
	@GetMapping("public/uris")
	List<UriModel> uris();

	@Operation(summary = "Retrieve URI for a component and type", description = "Fetches a specific URI for a given component and type.")
	@GetMapping("uri/component/{componentId}/type/{type}")
	UriModel componentUri(@PathVariable(required = true) @Parameter(description = "Component ID") int componentId,
			@PathVariable(required = true) @Parameter(description = "URI Type") UriType type);
}
