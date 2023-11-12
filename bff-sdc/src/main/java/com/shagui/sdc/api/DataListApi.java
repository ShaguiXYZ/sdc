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
public interface DataListApi {
    @Operation(summary = "Retrieve available data lists")
    @GetMapping("datalists")
    List<String> dataLists();

    @Operation(summary = "Retrieve data list values")
    @GetMapping("datalist/{datalistName}")
    List<String> dataListValues(@PathVariable @Parameter(description = "Datalist name") String datalistName);
}
