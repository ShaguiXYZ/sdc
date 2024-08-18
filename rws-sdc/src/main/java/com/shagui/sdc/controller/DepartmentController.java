package com.shagui.sdc.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.DepartmentRestApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.service.DepartmentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "departments", description = "API to maintain Departments")
public class DepartmentController implements DepartmentRestApi {
	private DepartmentService departmentService;

	@Override
	public DepartmentDTO department(int departmentId) {
		return departmentService.findById(departmentId);
	}

	@Override
	public PageData<DepartmentDTO> departments(Integer page, Integer ps) {
		if (page == null) {
			return departmentService.findAll();
		} else {
			return departmentService.findAll(new RequestPageInfo(page, ps));
		}
	}

	@Override
	public PageData<DepartmentDTO> departmentsByCompany(int companyId, Integer page, Integer ps) {
		if (page == null) {
			return departmentService.findByCompany(companyId);
		} else {
			return departmentService.findByCompany(companyId, new RequestPageInfo(page, ps));
		}
	}
}
