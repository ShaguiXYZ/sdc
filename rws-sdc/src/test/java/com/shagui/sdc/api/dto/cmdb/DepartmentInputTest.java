package com.shagui.sdc.api.dto.cmdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.shagui.sdc.model.CompanyModel;
import com.shagui.sdc.model.DepartmentModel;

class DepartmentInputTest {

	@Test
	void testConstructor() {
		DepartmentInput department = new DepartmentInput();
		assertNotNull(department);
	}

	@Test
	void testGettersAndSetters() {
		DepartmentInput department = new DepartmentInput();
		department.setId(1);
		department.setName("Test Department");
		department.setCia(123);
		List<SquadInput> squads = new ArrayList<>();
		department.setSquads(squads);

		assertEquals(1, department.getId());
		assertEquals("Test Department", department.getName());
		assertEquals(123, department.getCia());
		assertEquals(squads, department.getSquads());
	}

	@Test
	void testAsModel() {
		DepartmentInput department = new DepartmentInput();
		department.setId(1);
		department.setName("Test Department");
		department.setCia(123);
		List<SquadInput> squads = new ArrayList<>();
		department.setSquads(squads);

		DepartmentModel model = department.asModel();

		assertEquals(1, model.getId());
		assertEquals("Test Department", model.getName());
		assertEquals(123, model.getCompany().getId());
		assertEquals(squads, model.getSquads());
	}

	@Test
	void testPatch() {
		DepartmentInput department = new DepartmentInput();
		department.setName("Test Department");
		department.setCia(123);

		DepartmentModel model = new DepartmentModel(1);
		model.setName("Old Name");
		model.setCompany(new CompanyModel(456));

		model = department.patch(model);

		assertEquals(1, model.getId());
		assertEquals("Test Department", model.getName());
		assertEquals(123, model.getCompany().getId());
	}
}