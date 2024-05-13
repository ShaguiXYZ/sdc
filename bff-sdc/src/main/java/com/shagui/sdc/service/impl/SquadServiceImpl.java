package com.shagui.sdc.service.impl;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.service.SquadService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SquadServiceImpl implements SquadService {
	private final RwsSdcClient rwsSdcClient;

	@Override
	public SquadDTO squad(int squadId) {
		return rwsSdcClient.squad(squadId);
	}

	@Override
	public PageData<SquadDTO> squads(Integer page) {
		return rwsSdcClient.squads(page);
	}

	@Override
	public PageData<SquadDTO> departmentSquads(int departmentId, Integer page) {
		return rwsSdcClient.squadsByDepartment(departmentId, page);
	}

}
