package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;

public interface AnalysisService {
	PageData<MetricAnalysisDTO> analysis(int componentId);

	MetricAnalysisDTO analysis(int componentId, int metricId);

	PageData<MetricAnalysisDTO> metricHistory(int componentId, int metricId);

	PageData<MetricAnalysisDTO> metricHistory(int componentId, String metricName, String type);

	PageData<MetricAnalysisDTO> analize(int componentId);
}
