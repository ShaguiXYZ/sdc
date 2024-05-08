package com.shagui.sdc.api.dto.sonar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.shagui.sdc.api.domain.PageInfo;

class ComponentsSonarDTOTest {
    @Test
    void constructorAndSetterGetter() {
        ComponentsSonarDTO dto = new ComponentsSonarDTO();
        PageInfo expectedPageInfo = PageInfo.empty();
        List<ComponentSonarDTO> expectedComponents = new ArrayList<>();
        dto.setPaging(expectedPageInfo);
        dto.setComponents(expectedComponents);
        assertEquals(expectedPageInfo, dto.getPaging());
        assertEquals(expectedComponents, dto.getComponents());
    }
}
