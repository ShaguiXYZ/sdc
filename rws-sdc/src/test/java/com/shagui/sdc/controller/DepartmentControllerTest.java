package com.shagui.sdc.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.service.DepartmentService;

class DepartmentControllerTest {

	@InjectMocks
	private DepartmentController controller;

	@Mock
	private DepartmentService departmentService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void findByIdTest() {

		when(departmentService.findById(anyInt())).thenReturn(new DepartmentDTO());
		DepartmentDTO result = controller.department(1);
		assertNotNull(result);
	}

	@Test
	void findAllPageNullTest() {

		PageData<DepartmentDTO> departmentDto = new PageData<DepartmentDTO>(new ArrayList<DepartmentDTO>());

		when(departmentService.findAll()).thenReturn(departmentDto);
		PageData<DepartmentDTO> result = controller.departments(null, 1);
		assertNotNull(result);
	}

	@Test
	void findAllPageNotNullTest() {

		PageData<DepartmentDTO> departmentDto = new PageData<DepartmentDTO>(new ArrayList<DepartmentDTO>());

		when(departmentService.findAll(any(RequestPageInfo.class))).thenReturn(departmentDto);
		PageData<DepartmentDTO> result = controller.departments(1, 1);
		assertNotNull(result);
	}
}
