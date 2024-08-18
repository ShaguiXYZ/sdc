package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.DepartmentDTO;

public interface DepartmentService {
	DepartmentDTO findById(int departmentId);

	PageData<DepartmentDTO> findAll();

	PageData<DepartmentDTO> findAll(RequestPageInfo pageInfo);

	PageData<DepartmentDTO> findByCompany(int companyId);

	PageData<DepartmentDTO> findByCompany(int companyId, RequestPageInfo pageInfo);
}
