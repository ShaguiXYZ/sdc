package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;

public interface AnalysisService {
	PageData<MetricAnalysisDTO> analysis(int componentId);

	MetricAnalysisDTO analysis(int componentId, int metricId);

	PageData<MetricAnalysisDTO> metricHistory(int componentId, int metricId, Integer page,
			Integer ps);

	PageData<MetricAnalysisDTO> metricHistory(int componentId, String metricName, String type, Integer page,
			Integer ps);

	PageData<MetricAnalysisDTO> annualSum(String metricName, String metricType, Integer componentId,
			Integer squadId, Integer departmentId);

	PageData<MetricAnalysisDTO> analize(int componentId);
}
