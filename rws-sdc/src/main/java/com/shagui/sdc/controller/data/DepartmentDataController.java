package com.shagui.sdc.controller.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.data.DepartmentDataRestApi;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.input.DepartmentInput;
import com.shagui.sdc.service.DataMaintenanceService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "department maintenance", description = "API to department maintenance")
public class DepartmentDataController implements DepartmentDataRestApi{
	
	@Autowired
	private DataMaintenanceService dataMaintenanceService;

	@Override
	public DepartmentDTO department(DepartmentInput data) {
		return dataMaintenanceService.departmentData(data);
	}

	@Override
	public List<DepartmentDTO> departments(List<DepartmentInput> data) {
		return dataMaintenanceService.departmentsData(data);
	}

}
