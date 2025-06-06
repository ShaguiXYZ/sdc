package com.shagui.sdc.service;

import java.util.Map;
import java.util.Set;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.Range;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricDTO;

public interface ComponentService {
	ComponentDTO findBy(int componentId);

	ComponentDTO findBy(int squadId, String name);

	ComponentDTO create(ComponentDTO component);

	PageData<ComponentDTO> squadComponents(int squadId);

	PageData<ComponentDTO> squadComponents(int squadId, RequestPageInfo pageInfo);

	PageData<ComponentDTO> filter(String name, Integer squadId, Set<String> tags, Range range);

	PageData<ComponentDTO> filter(String name, Integer squadId, Set<String> tags, Range range,
			RequestPageInfo pageInfo);

	PageData<MetricDTO> componentMetrics(int componentId);

	Map<String, String> dictionary(int componentId);
}
