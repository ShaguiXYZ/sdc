package com.shagui.sdc.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.TagView;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
        MediaType.APPLICATION_JSON_VALUE })
public interface TagRestApi {
    @Operation(summary = "Retrieve all tags")
    @GetMapping("tags")
    public PageData<TagView> tags(
            @RequestParam(required = false) @Parameter(description = "Page number") Integer page,
            @RequestParam(required = false) @Parameter(description = "Page size") Integer ps);

    @Operation(summary = "Retrieve all component tags")
    @GetMapping("tags/component/{componentId}")
    public PageData<TagView> componentTags(
            @PathVariable @Parameter(description = "component identifier") int componentId,
            @RequestParam(required = false) @Parameter(description = "Page number") Integer page,
            @RequestParam(required = false) @Parameter(description = "Page size") Integer ps);
}
