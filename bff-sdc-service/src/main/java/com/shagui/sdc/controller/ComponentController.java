package com.shagui.sdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.ComponentRestApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.ComponentView;
import com.shagui.sdc.api.view.MetricView;
import com.shagui.sdc.service.ComponentService;
import com.shagui.sdc.util.Mapper;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "components", description = "API to maintain Components")
public class ComponentController implements ComponentRestApi {
	
	@Autowired
	private ComponentService componentService;

	@Override
	public PageData<ComponentView> filter(String name, Integer squadId, Integer page, Integer ps) {
		return Mapper.parse(componentService.filter(name, squadId, page, ps), ComponentView.class);
	}

	@Override
	public PageData<MetricView> componentMetrics(int componentId) {
		return Mapper.parse(componentService.componentMetrics(componentId), MetricView.class);
	}
}
