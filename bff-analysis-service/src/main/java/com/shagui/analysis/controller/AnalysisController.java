package com.shagui.analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.analysis.api.AnalysisRestApi;
import com.shagui.analysis.api.view.MetricAnalysisView;
import com.shagui.analysis.api.view.PageableView;
import com.shagui.analysis.service.AnalysisService;
import com.shagui.analysis.util.Mapper;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "analysis", description = "API to get analysis info")
public class AnalysisController implements AnalysisRestApi {
	@Autowired
	private AnalysisService analysisService;

	@Override
	public PageableView<MetricAnalysisView> metricHistory(int componentId, int metricId) {
		return Mapper.parse(analysisService.metricHistory(componentId, metricId));
	}

	@Override
	public PageableView<MetricAnalysisView> componentState(int componentId) {
		return Mapper.parse(analysisService.componentState(componentId));
	}
}
