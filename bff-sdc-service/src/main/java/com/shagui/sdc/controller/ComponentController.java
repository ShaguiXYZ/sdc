package com.shagui.sdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.ComponentRestApi;
import com.shagui.sdc.api.view.ComponentHistoricalCoverageView;
import com.shagui.sdc.api.view.PageableView;
import com.shagui.sdc.service.ComponentService;
import com.shagui.sdc.util.Mapper;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "components", description = "API to maintain Components")
public class ComponentController implements ComponentRestApi {
	
	@Autowired
	private ComponentService componentService;

	@Override
	public PageableView<ComponentHistoricalCoverageView> historicalCoverage(int componentId) {
		return Mapper.parse(componentService.historicalCoverage(componentId));
	}

}
