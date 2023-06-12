package com.shagui.sdc.controller.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shagui.sdc.api.data.DepartmentDataRestApi;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.cmdb.DepartmentInput;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.service.DataMaintenanceService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "department maintenance", description = "API to department maintenance")
public class DepartmentDataController implements DepartmentDataRestApi {
    
	@Value("classpath:data/departments-squads.json")
	Resource jsonDepartmentsSquads;

	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private DataMaintenanceService dataMaintenanceService;

	@Autowired
	private ResourceLoader resourceLoader;

	@Override
	public DepartmentDTO department(DepartmentInput data) {
		return dataMaintenanceService.departmentData(data);
	}

	@Override
	public List<DepartmentDTO> jsonDepartments() {
		try {
			InputStream is = jsonDepartmentsSquads.getInputStream();
			DepartmentInput[] input = mapper.readValue(is, DepartmentInput[].class);
			
			return departments(Arrays.asList(input));
		} catch (IOException e) {
			throw new SdcCustomException("Error reading departments", e);
		}
	}

	@Override
	public List<DepartmentDTO> jsonDepartments(String path) {
		Resource resource = resourceLoader.getResource("classpath:" + path);
		
		try {
			InputStream is = resource.getInputStream();
			DepartmentInput[] input = mapper.readValue(is, DepartmentInput[].class);
			
			return departments(Arrays.asList(input));
		} catch (IOException e) {
			throw new SdcCustomException("Error reading departments", e);
		}
	}

	@Override
	public List<DepartmentDTO> departments(List<DepartmentInput> data) {
		return dataMaintenanceService.departmentsData(data);
	}

}
