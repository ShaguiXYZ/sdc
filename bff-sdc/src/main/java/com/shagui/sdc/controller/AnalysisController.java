package com.shagui.sdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.AnalysisRestApi;
import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.MetricAnalysisView;
import com.shagui.sdc.service.AnalysisService;
import com.shagui.sdc.util.Mapper;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "analysis", description = "API to get analysis info")
public class AnalysisController implements AnalysisRestApi {
	@Autowired
	private AnalysisService analysisService;

	@Override
	public PageData<MetricAnalysisView> analysis(int componentId) {
		return Mapper.parse(analysisService.analysis(componentId), MetricAnalysisView.class);
	}

	@Override
	public MetricAnalysisView analysis(int componentId, int metricId) {
		return CastFactory.getInstance(MetricAnalysisView.class).parse(analysisService.analysis(componentId, metricId));
	}

	@Override
	public PageData<MetricAnalysisView> metricHistory(int componentId, int metricId) {
		return Mapper.parse(analysisService.metricHistory(componentId, metricId), MetricAnalysisView.class);
	}

	@Override
	public PageData<MetricAnalysisView> analyze(int componentId) {
		return Mapper.parse(analysisService.analize(componentId), MetricAnalysisView.class);
	}
}
