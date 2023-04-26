package com.shagui.sdc.api.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.shagui.sdc.api.dto.DepartmentDTO;

public class DepartmentViewTest {

	@Test
	public void constructorTest() {
		DepartmentView view = new DepartmentView();
		view.setCoverage((float) 90.1);
		view.setId(1);
		view.setName("test");

		DepartmentDTO dto = new DepartmentDTO();
		dto.setCoverage((float) 90.1);
		dto.setId(1);
		dto.setName("test");

		DepartmentView constructor = new DepartmentView(dto);

		assertEquals(constructor, view);
	}

}
