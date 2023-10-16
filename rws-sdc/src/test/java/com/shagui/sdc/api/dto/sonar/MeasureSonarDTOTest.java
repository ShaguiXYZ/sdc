package com.shagui.sdc.api.dto.sonar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MeasureSonarDTOTest {

    @Test
    void testConstructorAndGetters() {
        String metric = "coverage";
        String value = "80.0%";
        String component = "component";

        MeasureSonarDTO dto = new MeasureSonarDTO();
        dto.setComponent(component);
        dto.setMetric(metric);
        dto.setValue(value);

        assertEquals(component, dto.getComponent());
        assertEquals(metric, dto.getMetric());
        assertEquals(value, dto.getValue());
    }
}