package com.shagui.sdc.service.impl;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.service.AnalysisService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AnalysisServiceImpl implements AnalysisService {
	private RwsSdcClient rwsSdcClient;

	@Override
	public PageData<MetricAnalysisDTO> analysis(int componentId) {
		return rwsSdcClient.analysis(componentId);
	}

	@Override
	public MetricAnalysisDTO analysis(int componentId, int metricId) {
		return rwsSdcClient.analysis(componentId, metricId);
	}

	@Override
	public PageData<MetricAnalysisDTO> metricHistory(int componentId, int metricId, Integer page,
			Integer ps) {
		return rwsSdcClient.metricHistory(componentId, metricId, page, ps);
	}

	@Override
	public PageData<MetricAnalysisDTO> metricHistory(int componentId, String metricName, String type, Integer page,
			Integer ps) {
		return rwsSdcClient.metricHistory(componentId, metricName, type, page, ps);
	}

	@Override
	public PageData<MetricAnalysisDTO> annualSum(String metricName, String metricType, Integer componentId,
			Integer squadId, Integer departmentId) {
		return rwsSdcClient.annualSum(metricName, metricType, componentId, squadId, departmentId);
	}

	@Override
	public PageData<MetricAnalysisDTO> analize(int componentId) {
		return rwsSdcClient.analyze(componentId);
	}
}
