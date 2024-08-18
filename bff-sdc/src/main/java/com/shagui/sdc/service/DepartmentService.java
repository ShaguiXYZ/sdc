package com.shagui.sdc.service;

import java.util.List;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.DepartmentDTO;

public interface DepartmentService {
	DepartmentDTO department(int departmentId);

	PageData<DepartmentDTO> departments(Integer page);

	PageData<DepartmentDTO> departmentsByCompany(int companyId, Integer page);

	List<DepartmentDTO> updateDepartments();
}
