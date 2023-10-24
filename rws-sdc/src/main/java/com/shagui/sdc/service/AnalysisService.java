package com.shagui.sdc.service;

import java.util.Date;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.enums.AnalysisType;

public interface AnalysisService {
	PageData<MetricAnalysisDTO> analysis(int componentId);

	MetricAnalysisDTO analysis(int componentId, int metricId);

	PageData<MetricAnalysisDTO> analyze(int componentId);

	PageData<MetricAnalysisDTO> metricHistory(int componentId, int metricId, Date date);

	PageData<MetricAnalysisDTO> metricHistory(int componentId, String metricName, AnalysisType type, Date date);
}
