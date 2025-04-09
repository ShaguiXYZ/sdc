package com.shagui.sdc.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shagui.sdc.core.configuration.dto.SdcConfig;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface ConfigurationsApi {
    @Operation(summary = "Retrieve application configurations", description = "Fetches the current application configurations.")
    @GetMapping("public/configurations")
    SdcConfig configurations();
}
