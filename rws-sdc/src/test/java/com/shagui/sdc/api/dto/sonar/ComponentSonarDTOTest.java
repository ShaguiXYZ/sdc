package com.shagui.sdc.api.dto.sonar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ComponentSonarDTOTest {

    @Test
    void gettersAndSetters() {
        ComponentSonarDTO dto = new ComponentSonarDTO();
        dto.setKey("key");
        dto.setName("name");
        dto.setQualifier("qualifier");
        dto.setProject("project");

        assertEquals("key", dto.getKey());
        assertEquals("name", dto.getName());
        assertEquals("qualifier", dto.getQualifier());
        assertEquals("project", dto.getProject());
    }
}