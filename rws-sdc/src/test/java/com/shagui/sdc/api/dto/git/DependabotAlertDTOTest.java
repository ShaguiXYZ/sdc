package com.shagui.sdc.api.dto.git;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DependabotAlertDTOTest {
    @Test
    void testConstructorAndGettersAndSetters() {
        DependabotVulnerabilityDTO vulnerability = new DependabotVulnerabilityDTO();
        DependabotAlertDTO alert = new DependabotAlertDTO();

        alert.setId(1);
        alert.setState("open");
        alert.setVulnerability(vulnerability);

        assertEquals(1, alert.getId());
        assertEquals("open", alert.getState());
        assertEquals(vulnerability, alert.getVulnerability());
    }
}