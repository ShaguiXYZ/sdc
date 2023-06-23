package com.shagui.sdc.controller.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.data.ComponentDataRestApi;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.ebs.ComponentInput;
import com.shagui.sdc.service.DataMaintenanceService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "components maintenance", description = "API to components maintenance")
public class ComponentDataController implements ComponentDataRestApi {
	@Autowired
	private DataMaintenanceService dataMaintenanceService;

	@Override
	public ComponentDTO component(ComponentInput data) {
		return dataMaintenanceService.componentData(data);
	}
}
