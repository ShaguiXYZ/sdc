package com.shagui.sdc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.ConfigurationsApi;
import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.config.SdcConfig;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "configurations", description = "API to retrieve application configurations")
public class ConfigurationsController implements ConfigurationsApi {
    private final RwsSdcClient rwsSdcClient;

    public ConfigurationsController(@Value("${application.info.app.version}") String bffVersion,
            RwsSdcClient rwsSdcClient) {
        this.rwsSdcClient = rwsSdcClient;
        SdcConfig.setBffVersion(bffVersion);
    }

    @Override
    public SdcConfig configurations() {
        return rwsSdcClient.configurations();
    }
}
