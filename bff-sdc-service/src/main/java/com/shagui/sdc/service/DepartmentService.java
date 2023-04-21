package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.DepartmentDTO;

public interface DepartmentService {
	DepartmentDTO department(int departmentId);

	PageData<DepartmentDTO> departments(Integer page);
}
