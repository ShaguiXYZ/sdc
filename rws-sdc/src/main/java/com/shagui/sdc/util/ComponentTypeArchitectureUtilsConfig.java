package com.shagui.sdc.util;

import org.springframework.stereotype.Component;

import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.ComponetTypeArchitectureMetricPropertiesModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.MetricValuesModel;
import com.shagui.sdc.repository.ComponentTypeArchitectureMetricPropertiesRepository;
import com.shagui.sdc.repository.ComponentTypeArchitectureRepository;
import com.shagui.sdc.repository.MetricRepository;
import com.shagui.sdc.repository.MetricValueRepository;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ComponentTypeArchitectureUtilsConfig {
    private final ComponentTypeArchitectureRepository componentTypeArchitectureRepository;
    private final MetricRepository metricRepository;
    private final ComponentTypeArchitectureMetricPropertiesRepository componentTypeArchitectureMetricPropertiesRepository;
    private final MetricValueRepository metricValueRepository;

    public JpaCommonRepository<ComponentTypeArchitectureRepository, ComponentTypeArchitectureModel, Integer> componentTypeArchitectureRepository() {
        return () -> componentTypeArchitectureRepository;
    }

    public JpaCommonRepository<MetricRepository, MetricModel, Integer> metricRepository() {
        return () -> metricRepository;
    }

    public JpaCommonRepository<ComponentTypeArchitectureMetricPropertiesRepository, ComponetTypeArchitectureMetricPropertiesModel, Integer> componentTypeArchitectureMetricPropertiesRepository() {
        return () -> componentTypeArchitectureMetricPropertiesRepository;
    }

    public JpaCommonRepository<MetricValueRepository, MetricValuesModel, Integer> metricValueRepository() {
        return () -> metricValueRepository;
    }

    @PostConstruct
    public void init() {
        ComponentTypeArchitectureUtils.setConfig(this);
    }
}
