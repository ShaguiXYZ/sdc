package com.shagui.sdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.ComponentRestApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.Range;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.service.ComponentService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "components", description = "API to maintain Components")
public class ComponentController implements ComponentRestApi {

	@Autowired
	private ComponentService componentService;

	@Override
	public ComponentDTO create(ComponentDTO component) {
		return componentService.create(component);
	}

	@Override
	public ComponentDTO update(int id, ComponentDTO component) {
		return componentService.update(id, component);
	}

	@Override
	public PageData<ComponentDTO> filter(String name, Integer squadId, Float coverageMin, Float coverageMax,
			Integer page, Integer ps) {
		if (page == null) {
			return componentService.filter(name, squadId, new Range(coverageMin, coverageMax));
		} else {
			return componentService.filter(name, squadId, new Range(coverageMin, coverageMax),
					new RequestPageInfo(page, ps));
		}
	}

	@Override
	public PageData<MetricDTO> componentMetrics(int componentId) {
		return componentService.componentMetrics(componentId);
	}
}
