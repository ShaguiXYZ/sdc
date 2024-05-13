package com.shagui.sdc.service.impl;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.domain.HistoricalCoverage;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.service.ComponentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ComponentServiceImpl implements ComponentService {
	private final RwsSdcClient rwsSdcClient;

	@Override
	public ComponentDTO findBy(int componentId) {
		return rwsSdcClient.component(componentId);
	}

	@Override
	public PageData<ComponentDTO> squadComponents(int squadId, Integer page, Integer ps) {
		return rwsSdcClient.squadComponents(squadId, page, ps);
	}

	@Override
	public PageData<ComponentDTO> filter(String name, Integer squadId, Set<String> tags, Float coverageMin,
			Float coverageMax, Integer page, Integer ps) {
		return rwsSdcClient.filter(name, squadId, tags, coverageMin, coverageMax, page, ps);
	}

	@Override
	public PageData<MetricDTO> componentMetrics(int componentId) {
		return rwsSdcClient.componentMetrics(componentId);
	}

	@Override
	public HistoricalCoverage<ComponentDTO> historical(int componentId, Integer page, Integer ps) {
		return rwsSdcClient.componentHistoricalCoverage(componentId, page, ps);
	}
}
