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
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface DatalistApi {
	@Operation(summary = "Retrieve available datalists", description = "Fetches a list of all available datalists.")
	@GetMapping("public/datalist/availables")
	List<String> availables();

	@Operation(summary = "Retrieve datalist values", description = "Fetches the values of a specific datalist by name.")
	@GetMapping("public/datalist/{datalistName}")
	List<String> datalistValues(@PathVariable @Parameter(description = "Datalist name") String datalistName);
}
