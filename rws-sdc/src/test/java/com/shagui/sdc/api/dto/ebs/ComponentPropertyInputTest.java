package com.shagui.sdc.api.dto.ebs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.shagui.sdc.model.ComponentPropertyModel;

class ComponentPropertyInputTest {

    @Test
    void testConstructor() {
        ComponentPropertyInput componentPropertyInput = new ComponentPropertyInput();
        Assertions.assertNotNull(componentPropertyInput);
    }

    @Test
    void testSetName() {
        ComponentPropertyInput componentPropertyInput = new ComponentPropertyInput();
        componentPropertyInput.setName("testName");
        Assertions.assertEquals("testName", componentPropertyInput.getName());
    }

    @Test
    void testGetValue() {
        ComponentPropertyInput componentPropertyInput = new ComponentPropertyInput();
        componentPropertyInput.setValue("testValue");
        Assertions.assertEquals("testValue", componentPropertyInput.getValue());
    }

    @Test
    void testAsModel() {
        ComponentPropertyInput componentPropertyInput = new ComponentPropertyInput();
        componentPropertyInput.setName("testName");
        componentPropertyInput.setValue("testValue");

        ComponentPropertyModel componentPropertyModel = componentPropertyInput.asModel();

        Assertions.assertEquals("testName", componentPropertyModel.getName());
        Assertions.assertEquals("testValue", componentPropertyModel.getValue());
    }
}