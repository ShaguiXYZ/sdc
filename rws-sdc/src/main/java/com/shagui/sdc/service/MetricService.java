package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricDTO;

public interface MetricService {
	MetricDTO metric(int metricId);

	PageData<MetricDTO> metrics();

	MetricDTO create(MetricDTO metric);

	MetricDTO update(Integer id, MetricDTO metric);
}
