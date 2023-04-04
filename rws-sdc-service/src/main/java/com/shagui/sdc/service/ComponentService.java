package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricDTO;

public interface ComponentService {
	ComponentDTO create(ComponentDTO component);

	ComponentDTO update(Integer id, ComponentDTO component);

	PageData<ComponentDTO> filter(String name, Integer squadId);

	PageData<ComponentDTO> filter(String name, Integer squadId, RequestPageInfo pageInfo);

	PageData<MetricDTO> componentMetrics(int componentId);
}
