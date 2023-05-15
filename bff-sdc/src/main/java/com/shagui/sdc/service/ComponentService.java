package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.HistoricalCoverage;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricDTO;

public interface ComponentService {
	ComponentDTO findBy(int componentId);

	PageData<ComponentDTO> squadComponents(int squadId, Integer page, Integer ps);

	PageData<ComponentDTO> filter(String name, Integer squadId, Float coverageMin, Float coverageMax, Integer page,
			Integer ps);

	PageData<MetricDTO> componentMetrics(int componentId);

	HistoricalCoverage<ComponentDTO> historical(int componentId, Integer page, Integer ps);
}