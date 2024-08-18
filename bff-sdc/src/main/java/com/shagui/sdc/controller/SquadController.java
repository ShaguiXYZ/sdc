package com.shagui.sdc.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.SquadRestApi;
import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.view.SquadView;
import com.shagui.sdc.service.SquadService;
import com.shagui.sdc.util.Mapper;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "squads", description = "API to maintain Squads")
public class SquadController implements SquadRestApi {
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
	public PageData<SquadView> squadsByDepartment(int departmentId, Integer page) {
		return Mapper.parse(squadService.squadsByDepartment(departmentId, page), SquadView.class);
	}

	@Override
	public PageData<SquadView> squadsByCompany(int companyId, Integer page) {
		return Mapper.parse(squadService.squadsByCompany(companyId, page), SquadView.class);
	}

	@Override
	public Long countWithCoverage() {
		return squadService.countWithCoverage();
	}
}
