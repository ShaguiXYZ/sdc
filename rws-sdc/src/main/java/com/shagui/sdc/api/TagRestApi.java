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
import com.shagui.sdc.api.dto.ComponentTagDTO;
import com.shagui.sdc.api.dto.TagDTO;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface TagRestApi {
        @Operation(summary = "Retrieve all tags", description = "Fetches all available tags.")
        @GetMapping("public/tags")
        PageData<TagDTO> tags(
                        @RequestParam(required = false) @Parameter(description = "Page number") Integer page,
                        @RequestParam(required = false) @Parameter(description = "Page size") Integer ps);

        @Operation(summary = "Retrieve all component tags", description = "Fetches all tags associated with a specific component.")
        @GetMapping("public/tags/component/{componentId}")
        PageData<TagDTO> componentTags(
                        @PathVariable @Parameter(description = "Component identifier") int componentId,
                        @RequestParam(required = false) @Parameter(description = "Page number") Integer page,
                        @RequestParam(required = false) @Parameter(description = "Page size") Integer ps);

        @Operation(summary = "Create new tag")
        @PostMapping("tag/create/{componentId}/{name}")
        @ResponseStatus(HttpStatus.CREATED)
        ComponentTagDTO create(@PathVariable int componentId, @PathVariable String name);

        @Operation(summary = "Delete a tag from a component")
        @DeleteMapping("tag/delete/{componentId}/{name}")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public void delete(@PathVariable int componentId, @PathVariable String name);
}
