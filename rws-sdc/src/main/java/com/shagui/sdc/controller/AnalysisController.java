package com.shagui.sdc.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.AnalysisRestApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.service.AnalysisService;
import com.shagui.sdc.service.ComponentService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "analysis", description = "API to analyze components")
public class AnalysisController implements AnalysisRestApi {
	@Autowired
	private ComponentService componentService;
	
	@Autowired
	private AnalysisService analysisService;

	@Override
	public PageData<MetricAnalysisDTO> analysis(int componentId) {
		return analysisService.analysis(componentId);
	}

	@Override
	public MetricAnalysisDTO analysis(int componentId, int metricId) {
		return analysisService.analysis(componentId, metricId);
	}

	@Override
	public PageData<MetricAnalysisDTO> analyze(int squadId, String componentName) {
		ComponentDTO component = componentService.findBy(squadId, componentName);
		
		return analyze(component.getId());
	}

	@Override
	public PageData<MetricAnalysisDTO> analyze(int componentId) {
		return analysisService.analyze(componentId);
	}

	@Override
	public PageData<MetricAnalysisDTO> metricHistory(int componentId, int metricId, Date from) {
		return analysisService.metricHistory(componentId, metricId, from == null ? new Date() : from);
	}
}
