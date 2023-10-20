package com.shagui.sdc.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.cmdb.DepartmentInput;
import com.shagui.sdc.controller.data.DepartmentDataController;
import com.shagui.sdc.service.DataMaintenanceService;

class DepartmentDataControllerTest {

	@InjectMocks
	private DepartmentDataController controller;

	@Mock
	private DataMaintenanceService dataMaintenanceService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void departmentTest() {
		when(dataMaintenanceService.departmentUpdateData(any(DepartmentInput.class))).thenReturn(new DepartmentDTO());
		DepartmentDTO result = controller.department(new DepartmentInput());
		assertNotNull(result);
	}

	@Test
	void departmentsTest() {
		List<DepartmentInput> source = new ArrayList<>();
		when(dataMaintenanceService.departmentsUpdateData(anyList())).thenReturn(new ArrayList<>());
		List<DepartmentDTO> result = controller.departments(source);
		assertNotNull(result);
	}
}
