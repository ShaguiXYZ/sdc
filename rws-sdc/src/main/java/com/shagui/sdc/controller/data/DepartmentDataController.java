package com.shagui.sdc.controller.data;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.data.DepartmentDataRestApi;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.cmdb.DepartmentInput;
import com.shagui.sdc.service.DataMaintenanceService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "department maintenance", description = "API to department maintenance")
public class DepartmentDataController implements DepartmentDataRestApi {
	private DataMaintenanceService dataMaintenanceService;

	@Override
	public DepartmentDTO department(DepartmentInput data) {
		return dataMaintenanceService.departmentUpdateData(data);
	}

	@Override
	public List<DepartmentDTO> departments(List<DepartmentInput> data) {
		return dataMaintenanceService.departmentsUpdateData(data);
	}

	@Override
	public List<DepartmentDTO> jsonDepartments() {
		return dataMaintenanceService.jsonUpdateDepartments();
	}

	@Override
	public List<DepartmentDTO> jsonDepartments(String path) {
		return dataMaintenanceService.jsonUpdateDepartments(path);
	}
}
