package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;

public interface AnalysisService {
	PageData<MetricAnalysisDTO> metricHistory(int componentId, int metricId);
}