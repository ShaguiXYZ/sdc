package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricDTO;

public interface ComponentService {
	PageData<ComponentDTO> filter(String name, Integer squadId, Float coverageMin, Float coverageMax, Integer page,
			Integer ps);

	PageData<MetricDTO> componentMetrics(int componentId);
}
