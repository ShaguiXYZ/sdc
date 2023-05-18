package com.shagui.sdc.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.test.utils.DtoDataUtils;

class DepartmentServiceTest {

	@InjectMocks
	DepartmentServiceImpl departmentService;

	@Mock
	private RwsSdcClient rwsSdcClient;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void departmentTest() {
		DepartmentDTO value = DtoDataUtils.createDepartment();
		when(rwsSdcClient.department(anyInt())).thenReturn(value);
		DepartmentDTO result = departmentService.department(1);
		
		assertEquals(result, value);
	}

	@Test
	void departmentsTest() {
		PageData<DepartmentDTO> value = new PageData<DepartmentDTO>();
		when(rwsSdcClient.departments(anyInt())).thenReturn(value);
		PageData<DepartmentDTO> result = departmentService.departments(1);
		
		assertEquals(result, value);
	}
}
