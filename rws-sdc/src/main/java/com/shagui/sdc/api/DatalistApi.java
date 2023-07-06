package com.shagui.sdc.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/datalist" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface DatalistApi {
	@Operation(summary = "Retrieves the available datalists")
	@GetMapping
	List<String> availables();

	@Operation(summary = "Retrieves the datalist values")
	@GetMapping("{datalist}/values")
	List<String> datalistValues(@PathVariable @Parameter(description = "Datalist name") String datalist);
}
