package com.shagui.analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.analysis.api.SquadRestApi;
import com.shagui.analysis.api.view.ComponentsView;
import com.shagui.analysis.service.SquadService;
import com.shagui.analysis.util.Mapper;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "squads", description = "API to maintain Squads")
public class SquadController implements SquadRestApi {
	
	@Autowired
	private SquadService squadService;

	@Override
	public ComponentsView squadComponents(int sqadId, int page) {
		return Mapper.parse(squadService.squadComponents(sqadId, page));
	}
}
