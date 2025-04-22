package com.shagui.sdc.api.data;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.cmdb.DepartmentInput;

import feign.Headers;
import io.swagger.v3.oas.annotations.Operation;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = { "/api" }, produces = { MediaType.APPLICATION_JSON_VALUE })
public interface DepartmentDataRestApi {
	@Operation(summary = "Update department data", description = "Update department data")
	@PostMapping("data/department")
	DepartmentDTO department(@RequestBody DepartmentInput data);

	@Operation(summary = "Update departments data", description = "Update departments data")
	@PostMapping("data/departments")
	List<DepartmentDTO> departments(@RequestBody List<DepartmentInput> data);

	@Operation(summary = "Update departments data from default json", description = "Update departments data")
	@PostMapping("data/jsonDefaultDepartments")
	List<DepartmentDTO> jsonDepartments();
}
