package com.shagui.sdc.service;

import java.util.List;

import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.cmdb.DepartmentInput;
import com.shagui.sdc.api.dto.ebs.ComponentInput;

public interface DataMaintenanceService {
	DepartmentDTO departmentUpdateData(DepartmentInput data);

	List<DepartmentDTO> departmentsUpdateData(List<DepartmentInput> data);

	ComponentDTO componentUpdateData(ComponentInput data);

	List<DepartmentDTO> jsonUpdateDepartments();

	List<DepartmentDTO> jsonUpdateDepartments(String path);
}
