package com.shagui.sdc.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.ConfigurationsApi;
import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.view.SdcConfig;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "configurations", description = "API to retrieve application configurations")
public class ConfigurationsController implements ConfigurationsApi {
    private final RwsSdcClient rwsSdcClient;

    @Override
    public SdcConfig configurations() {
        return rwsSdcClient.configurations();
    }
}
