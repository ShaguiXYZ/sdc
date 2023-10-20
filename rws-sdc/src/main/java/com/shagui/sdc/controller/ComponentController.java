package com.shagui.sdc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.ComponentRestApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.Range;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.api.dto.ebs.ComponentInput;
import com.shagui.sdc.service.AnalysisService;
import com.shagui.sdc.service.ComponentService;
import com.shagui.sdc.service.DataMaintenanceService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "components", description = "API to maintain Components")
public class ComponentController implements ComponentRestApi {

	@Autowired
	private AnalysisService analysisService;

	@Autowired
	private ComponentService componentService;

	@Autowired
	private DataMaintenanceService dataMaintenanceService;

	@Override
	public ComponentDTO component(int componentId) {
		return componentService.findBy(componentId);
	}

	@Override
	public ComponentDTO create(ComponentInput data) {
		ComponentDTO dto = dataMaintenanceService.componentUpdateData(data);
		analysisService.analyze(dto.getId());

		return componentService.findBy(dto.getId());
	}

	@Override
	public ComponentDTO update(int id, ComponentDTO component) {
		return componentService.update(id, component);
	}

	@Override
	public PageData<ComponentDTO> squadComponents(int squadId, Integer page, Integer ps) {
		if (page == null) {
			return componentService.squadComponents(squadId);
		} else {
			return componentService.squadComponents(squadId, new RequestPageInfo(page, ps));
		}
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

	@Override
	public Map<String, String> dictionary(int componentId) {
		return componentService.dictionary(componentId);
	}
}
