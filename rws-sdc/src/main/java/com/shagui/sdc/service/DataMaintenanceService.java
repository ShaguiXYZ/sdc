package com.shagui.sdc.service;

import java.util.List;

import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.cmdb.ComponentInput;
import com.shagui.sdc.api.dto.cmdb.DepartmentInput;

public interface DataMaintenanceService {
	DepartmentDTO departmentData(DepartmentInput data);

	List<DepartmentDTO> departmentsData(List<DepartmentInput> data);

	ComponentDTO componentData(ComponentInput data);
}