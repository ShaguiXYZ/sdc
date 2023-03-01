package com.shagui.analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.analysis.api.SquadRestApi;
import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.api.dto.SquadDTO;
import com.shagui.analysis.service.ComponentService;
import com.shagui.analysis.service.SquadService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "squads", description = "API to maintain Squads")
public class SquadController implements SquadRestApi {
	
	@Autowired
	private SquadService squadService;
	
	@Autowired
	private ComponentService componentService;

	@Override
	public PageableDTO<ComponentDTO> squadComponents(int sqadId, Integer page) {
		return componentService.findBySquad(sqadId, page == null ? 0 : page);
	}

	@Override
	public PageableDTO<SquadDTO> squads(Integer page) {
		if (page != null) {
			return squadService.findAll(page);
		} else {
			return squadService.findAll();
		}
	}
}
