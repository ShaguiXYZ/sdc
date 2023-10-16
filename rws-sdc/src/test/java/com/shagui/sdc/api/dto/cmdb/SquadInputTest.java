package com.shagui.sdc.api.dto.cmdb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.shagui.sdc.model.SquadModel;

class SquadInputTest {

    private SquadInput squadInput;

    @BeforeEach
    void setUp() {
        squadInput = new SquadInput();
        squadInput.setId(1);
        squadInput.setDepartmentId(2);
        squadInput.setName("Test Squad");
        squadInput.setCia(3);
        List<BusinessServiceInput> businessServices = new ArrayList<>();
        businessServices.add(new BusinessServiceInput());
        squadInput.setBusinessServices(businessServices);
    }

    @Test
    void testGetters() {
        assertEquals(1, squadInput.getId());
        assertEquals(2, squadInput.getDepartmentId());
        assertEquals("Test Squad", squadInput.getName());
        assertEquals(3, squadInput.getCia());
        assertEquals(1, squadInput.getBusinessServices().size());
    }

    @Test
    void testSetters() {
        squadInput.setId(4);
        squadInput.setDepartmentId(5);
        squadInput.setName("New Squad");
        squadInput.setCia(6);
        List<BusinessServiceInput> businessServices = new ArrayList<>();
        businessServices.add(new BusinessServiceInput());
        businessServices.add(new BusinessServiceInput());
        squadInput.setBusinessServices(businessServices);

        assertEquals(4, squadInput.getId());
        assertEquals(5, squadInput.getDepartmentId());
        assertEquals("New Squad", squadInput.getName());
        assertEquals(6, squadInput.getCia());
        assertEquals(2, squadInput.getBusinessServices().size());
    }

    @Test
    void testToModel() {
        SquadModel squadModel = squadInput.toModel();

        assertEquals(1, squadModel.getId());
        assertEquals("Test Squad", squadModel.getName());
        assertEquals(2, squadModel.getDepartment().getId());
    }

    @Test
    void testPatch() {
        SquadModel squadModel = new SquadModel();
        squadModel.setId(1);
        squadModel.setName("Old Squad");

        squadInput.patch(squadModel);

        assertEquals(1, squadModel.getId());
        assertEquals("Test Squad", squadModel.getName());
        assertEquals(2, squadModel.getDepartment().getId());
    }
}