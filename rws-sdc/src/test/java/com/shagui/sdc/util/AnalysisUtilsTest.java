package com.shagui.sdc.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shagui.sdc.api.dto.ServiceDataDTO;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.repository.MetricValueRepository;
import com.shagui.sdc.model.MetricValuesModel;

class AnalysisUtilsTest {

    @BeforeEach
    void init() {
        MetricValueRepository metricValueRepository = mock(MetricValueRepository.class);
        AnalysisUtils.setConfig(new AnalysisUtilsConfig(metricValueRepository));
        Mapper.setConfig(new MapperConfig(new ObjectMapper()));
    }

    @Test
    void notDataFoundSupplierReturnsNa() {
        ComponentModel component = new ComponentModel();
        component.setName("my-component");

        MetricModel metric = new MetricModel();
        metric.setName("my-metric");

        ServiceDataDTO serviceData = new ServiceDataDTO("workflow", component, metric);

        assertEquals("N/A", AnalysisUtils.notDataFound(serviceData).get());
    }

    @Test
    void metricCoverageReturnsNullWhenNoMetricValuesFound() {
        MetricValueRepository metricValueRepository = mock(MetricValueRepository.class);
        when(metricValueRepository.metricValuesByDate(anyInt(), anyInt(), any(Timestamp.class)))
                .thenReturn(List.of());

        AnalysisUtils.setConfig(new AnalysisUtilsConfig(metricValueRepository));

        ComponentTypeArchitectureModel architecture = new ComponentTypeArchitectureModel();
        architecture.setId(1);

        ComponentModel component = new ComponentModel();
        component.setId(1);
        component.setComponentTypeArchitecture(architecture);
        component.setName("component");

        MetricModel metric = new MetricModel();
        metric.setId(2);
        metric.setName("metric");

        ComponentAnalysisModel analysis = new ComponentAnalysisModel(component, metric, "100");

        assertNull(AnalysisUtils.metricCoverage(List.of(analysis)));
    }
}
