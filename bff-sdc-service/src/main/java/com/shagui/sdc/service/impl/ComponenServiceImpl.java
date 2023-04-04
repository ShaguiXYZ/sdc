package com.shagui.sdc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.service.ComponentService;

@Service
public class ComponenServiceImpl implements ComponentService {

	@Autowired
	private RwsSdcClient rwsSdcClient;

	@Override
	public PageData<ComponentDTO> filter(String name, Integer squadId, Integer page, Integer ps) {
		return rwsSdcClient.filter(name, squadId, page, ps);
	}

	@Override
	public PageData<MetricDTO> componentMetrics(int componentId) {
		return rwsSdcClient.componentMetrics(componentId);
	}

}
