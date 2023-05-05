package com.shagui.sdc.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.view.DepartmentView;
import com.shagui.sdc.service.DepartmentService;
import com.shagui.sdc.test.utils.DataUtils;

class DepartmentControllerTest {
	
	@InjectMocks
	DepartmentController departmentController;
	
	@Mock
	DepartmentService departmentService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void departmentTest() {
		DepartmentDTO dto = DataUtils.createDepartmentDTO();
		when(departmentService.department(anyInt())).thenReturn(dto);
		DepartmentView result = departmentController.department(DataUtils.DEFAULT_DEPARTMENT_ID);
		assertNotNull(result);
		assertEquals(result.getId(), dto.getId());
		assertEquals(result.getName(), dto.getName());
	}
	
	@Test
	void departmentsTest() {
		when(departmentService.departments(any())).thenReturn(DataUtils.createEmptyPageData());
		PageData<DepartmentView> result = departmentController.departments(null);
		assertNotNull(result);
	}
}
