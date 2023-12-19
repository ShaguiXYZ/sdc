package com.shagui.sdc.core.configuration.dto;

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
    private AnalysisConfig analysis;
    private JpaConfig jpa;

    public SdcConfig() {
        this.analysis = new AnalysisConfig();
        this.jpa = new JpaConfig();
    }
}
