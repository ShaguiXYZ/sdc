package com.shagui.analysis.service;

import com.shagui.analysis.api.dto.MetricDTO;

public interface MetricService {
	MetricDTO create(MetricDTO metric);
	MetricDTO update(Integer id, MetricDTO metric);
}
