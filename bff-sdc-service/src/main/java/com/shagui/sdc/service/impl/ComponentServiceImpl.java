package com.shagui.sdc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.dto.ComponentHistoricalCoverageDTO;
import com.shagui.sdc.api.dto.PageableDTO;
import com.shagui.sdc.service.ComponentService;

@Service
public class ComponentServiceImpl implements ComponentService {

	@Autowired
	private RwsSdcClient rwsSdcClient;

	@Override
	public PageableDTO<ComponentHistoricalCoverageDTO> historicalCoverage(int componentId) {
		return rwsSdcClient.historicalCoverage(componentId);
	}

}
