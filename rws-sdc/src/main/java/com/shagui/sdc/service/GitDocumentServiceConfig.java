package com.shagui.sdc.service;

import org.springframework.stereotype.Component;

import com.shagui.sdc.model.ComponetTypeArchitectureMetricPropertiesModel;
import com.shagui.sdc.repository.ComponentTypeArchitectureMetricPropertiesRepository;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GitDocumentServiceConfig {

    private ComponentTypeArchitectureMetricPropertiesRepository componentTypeArchitectureMetricPropertiesRep;

    public JpaCommonRepository<ComponentTypeArchitectureMetricPropertiesRepository, ComponetTypeArchitectureMetricPropertiesModel, Integer> componentTypeArchitectureMetricPropertiesRepository() {
        return () -> componentTypeArchitectureMetricPropertiesRep;
    }

    @PostConstruct
    public void init() {
        GitDocumentService.setConfig(this);
    }
}
