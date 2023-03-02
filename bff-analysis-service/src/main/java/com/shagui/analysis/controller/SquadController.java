package com.shagui.analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.analysis.api.SquadRestApi;
import com.shagui.analysis.api.view.ComponentView;
import com.shagui.analysis.api.view.MetricAnalysisStateView;
import com.shagui.analysis.api.view.PageableView;
import com.shagui.analysis.api.view.SquadView;
import com.shagui.analysis.service.SquadService;
import com.shagui.analysis.util.Mapper;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "squads", description = "API to maintain Squads")
public class SquadController implements SquadRestApi {
	
	@Autowired
	private SquadService squadService;

	@Override
	public PageableView<SquadView> squads(Integer page) {
		return Mapper.parse(squadService.squads(page));
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
