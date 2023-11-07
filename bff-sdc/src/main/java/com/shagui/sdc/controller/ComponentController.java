package com.shagui.sdc.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.ComponentRestApi;
import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.domain.HistoricalCoverage;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.ComponentView;
import com.shagui.sdc.api.view.MetricView;
import com.shagui.sdc.service.ComponentService;
import com.shagui.sdc.util.Mapper;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "components", description = "API to maintain Components")
public class ComponentController implements ComponentRestApi {
	private ComponentService componentService;

	@Override
	public ComponentView component(int componentId) {
		return CastFactory.getInstance(ComponentView.class).parse(componentService.findBy(componentId));
	}

	@Override
	public PageData<ComponentView> squadComponents(int squadId, Integer page, Integer ps) {
		return Mapper.parse(componentService.squadComponents(squadId, page, ps), ComponentView.class);
	}

	@Override
	public PageData<ComponentView> filter(String name, Integer squadId, Float coverageMin, Float coverageMax,
			Integer page, Integer ps) {
		return Mapper.parse(componentService.filter(name, squadId, coverageMin, coverageMax, page, ps),
				ComponentView.class);
	}

	@Override
	public PageData<MetricView> componentMetrics(int componentId) {
		return Mapper.parse(componentService.componentMetrics(componentId), MetricView.class);
	}

	@Override
	public HistoricalCoverage<ComponentView> historical(int componentId, Integer page, Integer ps) {
		return Mapper.parse(componentService.historical(componentId, page, ps), ComponentView.class);
	}
}