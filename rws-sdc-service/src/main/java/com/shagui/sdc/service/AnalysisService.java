package com.shagui.sdc.service;

import java.util.Date;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;

public interface AnalysisService {
	PageData<MetricAnalysisDTO> analyze(int componentId);
	PageData<MetricAnalysisDTO> metricHistory(int componentId, int metricId, Date date);
}
