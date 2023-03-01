package com.shagui.analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.analysis.api.ComponentRestApi;
import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.service.ComponentService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "components", description = "API to maintain Components")
public class ComponentController implements ComponentRestApi {
	
	@Autowired
	private ComponentService componentService;

	@Override
	public ComponentDTO create(ComponentDTO component) {
		return componentService.create(component);
	}

	@Override
	public ComponentDTO update(int id, ComponentDTO component) {
		return componentService.update(id, component);
	}
}