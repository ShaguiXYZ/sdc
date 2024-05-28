package com.shagui.sdc.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.UriRestApi;
import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.view.UriView;
import com.shagui.sdc.service.UriService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "uris", description = "API to maintain Uris")
public class UriController implements UriRestApi {
    private final UriService uriService;

    @Override
    public UriView componentUri(int componentId, String type) {
        return CastFactory.getInstance(UriView.class).parse(uriService.componentUri(componentId, type));
    }
}
