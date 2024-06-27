package com.shagui.sdc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.DepartmentRestApi;
import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.DepartmentView;
import com.shagui.sdc.service.DepartmentService;
import com.shagui.sdc.util.Mapper;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "departments", description = "API to maintain Departments")
public class DepartmentController implements DepartmentRestApi {
	private DepartmentService departmentService;

	@Override
	public DepartmentView department(int departmentId) {
		return CastFactory.getInstance(DepartmentView.class).parse(departmentService.department(departmentId));
	}

	@Override
	public PageData<DepartmentView> departments(Integer page) {
		return Mapper.parse(departmentService.departments(page), DepartmentView.class);
	}

	@Override
	public List<DepartmentView> updateDepartments() {
		return Mapper.parse(departmentService.updateDepartments(), DepartmentView.class);
	}
}
