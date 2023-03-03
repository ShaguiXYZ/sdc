package com.shagui.analysis.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.analysis.api.ComponentRestApi;
import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.MetricAnalysisStateDTO;
import com.shagui.analysis.service.ComponentService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "components", description = "API to maintain Components")
public class ComponentController implements ComponentRestApi {
	
	@Autowired
	private ComponentService componentService;

	@Override
	public MetricAnalysisStateDTO componentState(int componentId, Date from) {
		return componentService.componentState(componentId, from == null ? new Date() : from);
	}

	@Override
	public ComponentDTO create(ComponentDTO component) {
		return componentService.create(component);
	}

	@Override
	public ComponentDTO update(int id, ComponentDTO component) {
		return componentService.update(id, component);
	}
}
