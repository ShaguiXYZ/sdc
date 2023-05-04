package com.shagui.sdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.DepartmentRestApi;
import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.DepartmentView;
import com.shagui.sdc.service.DepartmentService;
import com.shagui.sdc.util.Mapper;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "departments", description = "API to maintain Departments")
public class DepartmentController implements DepartmentRestApi {

	@Autowired
	private DepartmentService departmentService;

	@Override
	public DepartmentView department(int departmentId) {
		return CastFactory.getInstance(DepartmentView.class).parse(departmentService.department(departmentId));
	}

	@Override
	public PageData<DepartmentView> departments(Integer page) {
		return Mapper.parse(departmentService.departments(page), DepartmentView.class);
	}

}
