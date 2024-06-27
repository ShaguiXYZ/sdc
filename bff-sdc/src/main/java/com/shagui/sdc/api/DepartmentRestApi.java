package com.shagui.sdc.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.DepartmentView;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface DepartmentRestApi {
	@Operation(summary = "Retrieve department by id")
	@GetMapping("department/{departmentId}")
	DepartmentView department(@PathVariable @Parameter(description = "Department identifier") int departmentId);

	@Operation(summary = "Retrieve available departments")
	@GetMapping("departments")
	PageData<DepartmentView> departments(
			@RequestParam(required = false) @Parameter(description = "Page number") Integer page);

	@Operation(summary = "Update departments")
	@PostMapping("departments/update")
	List<DepartmentView> updateDepartments();
}
