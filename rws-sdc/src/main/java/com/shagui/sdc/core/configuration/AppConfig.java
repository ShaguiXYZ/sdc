package com.shagui.sdc.core.configuration;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.shagui.sdc.core.configuration.dto.SdcConfig;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AppConfig {
    private final SdcConfig sdcConfigurations;

    private static final SdcConfig config = new SdcConfig();

    public static SdcConfig getConfig() {
        return config;
    }

    @PostConstruct
    public void init() {
        BeanUtils.copyProperties(sdcConfigurations, config);
    }
}
