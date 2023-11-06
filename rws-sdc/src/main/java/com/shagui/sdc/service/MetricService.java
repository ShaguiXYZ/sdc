package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.enums.AnalysisType;

public interface MetricService {
	MetricDTO metric(int metricId);

	MetricDTO metric(String metricName, AnalysisType metricType);

	PageData<MetricDTO> metrics();

	MetricDTO create(MetricDTO metric);

	MetricDTO update(Integer id, MetricDTO metric);
}
