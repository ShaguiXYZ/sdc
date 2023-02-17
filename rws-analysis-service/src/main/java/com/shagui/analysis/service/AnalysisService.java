package com.shagui.analysis.service;

import java.util.Date;
import java.util.List;

import com.shagui.analysis.api.dto.MetricAnalysisDTO;

public interface AnalysisService {
	List<MetricAnalysisDTO> analyze(int componentId);
	List<MetricAnalysisDTO> metricHistory(int componentId, int metricId, Date date);
	List<MetricAnalysisDTO> componentState(int componentId, Date date);
}
