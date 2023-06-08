package com.shagui.sdc.api.data;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.cmdb.ComponentInput;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/data" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface ComponentDataRestApi {
	@Operation(summary = "Update component data", description = "Update component data")
	@PatchMapping("component")
	ComponentDTO component(@RequestBody ComponentInput data);
}
