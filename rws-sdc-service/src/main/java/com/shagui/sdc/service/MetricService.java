package com.shagui.sdc.service;

import com.shagui.sdc.api.dto.MetricDTO;

public interface MetricService {
	MetricDTO create(MetricDTO metric);
	MetricDTO update(Integer id, MetricDTO metric);
}
