package com.shagui.sdc.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.ConfigurationsApi;
import com.shagui.sdc.core.configuration.AppConfig;
import com.shagui.sdc.core.configuration.dto.SdcConfig;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "configurations", description = "API retrieve application configurations")
public class ConfigurationsController implements ConfigurationsApi {
    public SdcConfig configurations() {
        return AppConfig.getConfig();
    }
}
