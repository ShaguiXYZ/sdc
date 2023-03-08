package com.shagui.analysis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.analysis.api.client.RwsSdcClient;
import com.shagui.analysis.api.dto.ComponentHistoricalCoverageDTO;
import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.service.ComponentService;

@Service
public class ComponentServiceImpl implements ComponentService {

	@Autowired
	private RwsSdcClient rwsSdcClient;

	@Override
	public PageableDTO<ComponentHistoricalCoverageDTO> historicalCoverage(int componentId) {
		return rwsSdcClient.historicalCoverage(componentId);
	}

}
