package com.shagui.sdc.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shagui.sdc.json.model.UriModel;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface UriRestApi {
	@Operation(summary = "Retrieves the available uris")
	@GetMapping("public/uris")
	List<UriModel> uris();
}
