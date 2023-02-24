package com.shagui.analysis.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.analysis.api.AnalysisRestApi;
import com.shagui.analysis.api.view.MetricAnalysisView;
import com.shagui.analysis.service.AnalysisService;
import com.shagui.analysis.util.Mapper;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "analysis", description = "API to get analysis info")
public class AnalysisController implements AnalysisRestApi {
	@Autowired
	private AnalysisService analysisService;

	@Override
	public List<MetricAnalysisView> metricHistory(int componentId, int metricId) {
		return analysisService.metricHistory(componentId, metricId).stream().map(Mapper::parse)
				.collect(Collectors.toList());
	}

	@Override
	public List<MetricAnalysisView> componentState(int componentId) {
		return analysisService.componentState(componentId).stream().map(Mapper::parse).collect(Collectors.toList());
	}
}
