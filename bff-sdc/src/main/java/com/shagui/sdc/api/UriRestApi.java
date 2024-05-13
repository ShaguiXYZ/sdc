package com.shagui.sdc.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shagui.sdc.api.view.UriView;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface UriRestApi {
	@Operation(summary = "Retrieves the available uri for a component and type")
	@GetMapping("uri/component/{componentId}/type/{type}")
	UriView componentUri(@PathVariable(required = true) @Parameter(description = "Component Id") int componentId,
			@PathVariable(required = true) @Parameter(description = "Uri Type") String type);
}
