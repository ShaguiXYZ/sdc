package com.shagui.analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.analysis.api.ComponentRestApi;
import com.shagui.analysis.api.view.ComponentHistoricalCoverageView;
import com.shagui.analysis.api.view.PageableView;
import com.shagui.analysis.service.ComponentService;
import com.shagui.analysis.util.Mapper;

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
