package com.shagui.sdc.core.configuration.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter()
@Setter()
@Configuration
@ConfigurationProperties(prefix = "application.configuration")
public class SdcConfig {
    @Value("${application.info.app.version}")
    private String rwsVersion;
    @Value("${application.info.app.copyright}")
    private String copyright;
    private AnalysisConfig analysis;
    private ViewConfig view;
    private SecurityConfig security;

    public SdcConfig() {
        this.analysis = new AnalysisConfig();
        this.view = new ViewConfig();
    }
}
