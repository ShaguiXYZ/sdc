package com.shagui.sdc.service;

import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.PageableDTO;

public interface AnalysisService {
	PageableDTO<MetricAnalysisDTO> metricHistory(int componentId, int metricId);
}
