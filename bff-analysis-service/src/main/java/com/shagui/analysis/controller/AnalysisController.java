package com.shagui.analysis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.analysis.api.AnalysisRestApi;
import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.service.AnalysisService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "analysis", description = "API to get analysis info")
public class AnalysisController implements AnalysisRestApi {
	@Autowired
	private AnalysisService analysisService;

	@Override
	public List<MetricAnalysisDTO> metricHistory(int componentId, int metricId) {
		return analysisService.metricHistory(componentId, metricId);
	}

	@Override
	public List<MetricAnalysisDTO> componentState(int componentId) {
		return analysisService.componentState(componentId);
	}
}
