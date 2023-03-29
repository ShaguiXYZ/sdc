package com.shagui.sdc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.service.SquadService;

@Service
public class SquadServiceImpl implements SquadService {

	@Autowired
	private RwsSdcClient rwsSdcClient;

	@Override
	public SquadDTO squad(int squadId) {
		return rwsSdcClient.squad(squadId);
	}

	@Override
	public PageData<SquadDTO> squads(Integer page) {
		return rwsSdcClient.squads(page);
	}

	@Override
	public PageData<SquadDTO> squads(int departmentId, Integer page) {
		return rwsSdcClient.squadsByDepartment(departmentId, page);
	}

	@Override
	public PageData<ComponentDTO> squadComponents(int squadId, Integer page, Integer ps) {
		return rwsSdcClient.squadComponents(squadId, page, ps);
	}

}
