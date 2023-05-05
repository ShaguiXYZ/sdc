package com.shagui.sdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.SquadRestApi;
import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.domain.PageData;
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
		return CastFactory.getInstance(SquadView.class).parse(squadService.squad(squadId));
	}

	@Override
	public PageData<SquadView> squads(Integer page) {
		return Mapper.parse(squadService.squads(page), SquadView.class);
	}

	@Override
	public PageData<SquadView> departmentSquads(int departmentId, Integer page) {
		return Mapper.parse(squadService.departmentSquads(departmentId, page), SquadView.class);
	}
}
