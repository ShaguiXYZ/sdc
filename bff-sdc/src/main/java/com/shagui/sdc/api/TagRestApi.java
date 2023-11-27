package com.shagui.sdc.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

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

        @Operation(summary = "Create new tag")
        @PostMapping("tag/create/{componentId}/{name}")
        @ResponseStatus(HttpStatus.CREATED)
        TagView create(@PathVariable int componentId, @PathVariable String name);

        @Operation(summary = "Delete a tag from a component")
        @DeleteMapping("tag/delete/{componentId}/{name}")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public void delete(@PathVariable int componentId, @PathVariable String name);
}
