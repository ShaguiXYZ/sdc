package com.shagui.sdc.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.SquadRestApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.service.SquadService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "squads", description = "API to maintain Squads")
public class SquadController implements SquadRestApi {
	private SquadService squadService;

	@Override
	public SquadDTO squad(int squadId) {
		return squadService.findById(squadId);
	}

	@Override
	public PageData<SquadDTO> squads(Integer page, Integer ps) {
		if (page == null) {
			return squadService.findAll();
		} else {
			return squadService.findAll(new RequestPageInfo(page, ps));
		}
	}

	@Override
	public PageData<SquadDTO> squadsByDepartment(int departmentId, Integer page, Integer ps) {
		if (page == null) {
			return squadService.findByDepartment(departmentId);
		} else {
			return squadService.findByDepartment(departmentId, new RequestPageInfo(page, ps));
		}
	}
}
