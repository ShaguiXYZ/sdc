package com.shagui.analysis.service;

import com.shagui.analysis.api.dto.ComponentStateDTO;
import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.api.dto.PageableDTO;

public interface AnalysisService {
	PageableDTO<MetricAnalysisDTO> metricHistory(int componentId, int metricId);
	ComponentStateDTO componentState(int componentId);
}
