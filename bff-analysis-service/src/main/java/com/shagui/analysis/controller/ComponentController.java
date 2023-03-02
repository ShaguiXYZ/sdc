package com.shagui.analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.analysis.api.ComponentRestApi;
import com.shagui.analysis.api.view.MetricAnalysisStateView;
import com.shagui.analysis.service.ComponentService;
import com.shagui.analysis.util.Mapper;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "analysis", description = "API to get analysis info")
public class ComponentController implements ComponentRestApi {
	@Autowired
	private ComponentService componentService;

	@Override
	public MetricAnalysisStateView componentState(int componentId) {
		return Mapper.parse(componentService.componentState(componentId));
	}
}
