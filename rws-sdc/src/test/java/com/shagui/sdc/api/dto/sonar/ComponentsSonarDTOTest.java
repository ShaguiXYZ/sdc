package com.shagui.sdc.api.dto.sonar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.shagui.sdc.api.domain.PageInfo;

class ComponentsSonarDTOTest {
    @Test
    void testConstructorAndSetterGetter() {
        ComponentsSonarDTO dto = new ComponentsSonarDTO();
        PageInfo expectedPageInfo = new PageInfo(0, 0, 0, 0);
        List<ComponentSonarDTO> expectedComponents = new ArrayList<>();
        dto.setPaging(expectedPageInfo);
        dto.setComponents(expectedComponents);
        assertEquals(expectedPageInfo, dto.getPaging());
        assertEquals(expectedComponents, dto.getComponents());
    }
}