package com.shagui.sdc.api;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.SummaryViewView;

import feign.Headers;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api/summary" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface SummaryViewApi {
    @GetMapping("filter")
    PageData<SummaryViewView> filter(
            @RequestParam(required = false) @Parameter(description = "Summary name") String name,
            @RequestParam(required = false) @Parameter(description = "Summary types") Set<String> types,
            @RequestParam(required = false) @Parameter(description = "Page number") Integer page,
            @RequestParam(required = false) @Parameter(description = "Page size") Integer ps);
}
