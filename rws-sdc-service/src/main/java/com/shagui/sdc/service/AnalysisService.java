package com.shagui.sdc.service;

import java.util.Date;

import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.PageableDTO;

public interface AnalysisService {
	PageableDTO<MetricAnalysisDTO> analyze(int componentId);
	PageableDTO<MetricAnalysisDTO> metricHistory(int componentId, int metricId, Date date);
}
