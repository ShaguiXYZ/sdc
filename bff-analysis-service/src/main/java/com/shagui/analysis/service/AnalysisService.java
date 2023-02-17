package com.shagui.analysis.service;

import java.util.List;

import com.shagui.analysis.api.dto.MetricAnalysisDTO;

public interface AnalysisService {
	List<MetricAnalysisDTO> metricHistory(int componentId, int metricId);
	List<MetricAnalysisDTO> componentState(int componentId);
}
