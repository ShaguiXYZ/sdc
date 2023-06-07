package com.shagui.sdc.controller.data;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.data.ComponentDataRestApi;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.input.ComponentInput;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "components maintenance", description = "API to components maintenance")
public class ComponentDataController implements ComponentDataRestApi {

	@Override
	public ComponentDTO component(ComponentInput data) {
		// TODO Auto-generated method stub
		return null;
	}

}
