package com.shagui.sdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.DepartmentRestApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.service.DepartmentService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "departments", description = "API to maintain Departments")
public class DepartmentController implements DepartmentRestApi {
	
	@Autowired
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

}
