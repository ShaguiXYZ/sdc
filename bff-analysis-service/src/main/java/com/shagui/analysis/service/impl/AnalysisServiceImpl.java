package com.shagui.analysis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.analysis.api.client.RwsSdcClient;
import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.service.AnalysisService;

@Service
public class AnalysisServiceImpl implements AnalysisService {

	@Autowired
	private RwsSdcClient rwsSdcClient;

	@Override
	public List<MetricAnalysisDTO> metricHistory(int componentId, int metricId) {
		return rwsSdcClient.metricHistory(componentId, metricId);
	}

	@Override
	public List<MetricAnalysisDTO> componentState(int componentId) {
		return rwsSdcClient.componentState(componentId);
	}
}
