package com.shagui.sdc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.service.AnalysisService;

@Service
public class AnalysisServiceImpl implements AnalysisService {

	@Autowired
	private RwsSdcClient rwsSdcClient;

	@Override
	public MetricAnalysisDTO analysis(int componentId, int metricId) {
		return rwsSdcClient.analysis(componentId, metricId);
	}

	@Override
	public PageData<MetricAnalysisDTO> metricHistory(int componentId, int metricId) {
		return rwsSdcClient.metricHistory(componentId, metricId);
	}
}
