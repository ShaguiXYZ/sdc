package com.shagui.analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.analysis.api.SquadRestApi;
import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.PaginatedDTO;
import com.shagui.analysis.service.ComponentService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "squads", description = "API to maintain Squads")
public class SquadController implements SquadRestApi {
	
	@Autowired
	private ComponentService componentService;

	@Override
	public PaginatedDTO<ComponentDTO> squadComponents(int sqadId, int page) {
		return componentService.findBySquad(sqadId, page);
	}
}
