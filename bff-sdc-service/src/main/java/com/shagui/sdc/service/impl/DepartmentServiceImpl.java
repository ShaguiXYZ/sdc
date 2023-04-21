package com.shagui.sdc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private RwsSdcClient rwsSdcClient;

	@Override
	public DepartmentDTO department(int departmentId) {
		return rwsSdcClient.department(departmentId);
	}

	@Override
	public PageData<DepartmentDTO> departments(Integer page) {
		return rwsSdcClient.departments(page);
	}
}
