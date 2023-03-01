package com.shagui.analysis.service;

import java.util.Date;

import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.api.dto.PageableDTO;

public interface AnalysisService {
	PageableDTO<MetricAnalysisDTO> analyze(int componentId);
	PageableDTO<MetricAnalysisDTO> metricHistory(int componentId, int metricId, Date date);
	PageableDTO<MetricAnalysisDTO> componentState(int componentId, Date date);
}
