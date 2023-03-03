package com.shagui.analysis.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.analysis.api.AnalysisRestApi;
import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.service.AnalysisService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "analysis", description = "API to analyze components")
public class AnalysisController implements AnalysisRestApi {
	@Autowired
	private AnalysisService analysisService;

	@Override
	public PageableDTO<MetricAnalysisDTO> analyzeComponent(int componentId) {
		return analysisService.analyze(componentId);
	}

	@Override
	public PageableDTO<MetricAnalysisDTO> metricHistory(int componentId, int metricId, Date from) {
		return analysisService.metricHistory(componentId, metricId, from == null ? new Date() : from);
	}
}
