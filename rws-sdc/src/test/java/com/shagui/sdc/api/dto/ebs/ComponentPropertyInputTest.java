package com.shagui.sdc.api.dto.ebs;

import org.junit.jupiter.api.Test;

import com.shagui.sdc.model.ComponentPropertyModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ComponentPropertyInputTest {

    @Test
    void constructor() {
        ComponentPropertyInput componentPropertyInput = new ComponentPropertyInput();
        assertNotNull(componentPropertyInput);
    }

    @Test
    void setName() {
        ComponentPropertyInput componentPropertyInput = new ComponentPropertyInput();
        componentPropertyInput.setName("testName");
        assertEquals("testName", componentPropertyInput.getName());
    }

    @Test
    void getValue() {
        ComponentPropertyInput componentPropertyInput = new ComponentPropertyInput();
        componentPropertyInput.setValue("testValue");
        assertEquals("testValue", componentPropertyInput.getValue());
    }

    @Test
    void asModel() {
        ComponentPropertyInput componentPropertyInput = new ComponentPropertyInput();
        componentPropertyInput.setName("testName");
        componentPropertyInput.setValue("testValue");

        ComponentPropertyModel componentPropertyModel = componentPropertyInput.asModel();

        assertEquals("testName", componentPropertyModel.getName());
        assertEquals("testValue", componentPropertyModel.getValue());
    }
}