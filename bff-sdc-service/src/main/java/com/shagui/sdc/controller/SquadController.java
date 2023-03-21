package com.shagui.sdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.SquadRestApi;
import com.shagui.sdc.api.view.ComponentView;
import com.shagui.sdc.api.view.MetricAnalysisStateView;
import com.shagui.sdc.api.view.PageableView;
import com.shagui.sdc.api.view.SquadView;
import com.shagui.sdc.service.SquadService;
import com.shagui.sdc.util.Mapper;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "squads", description = "API to maintain Squads")
public class SquadController implements SquadRestApi {

	@Autowired
	private SquadService squadService;

	@Override
	public SquadView squad(int squadId) {
		return Mapper.parse(squadService.squad(squadId));
	}

	@Override
	public PageableView<SquadView> squads(int departmentId, Integer page) {
		return Mapper.parse(squadService.squads(departmentId, page));
	}

	@Override
	public MetricAnalysisStateView squadState(int squadId) {
		return Mapper.parse(squadService.squadState(squadId));
	}

	@Override
	public PageableView<ComponentView> squadComponents(int squadId, Integer page) {
		return Mapper.parse(squadService.squadComponents(squadId, page));
	}
}
