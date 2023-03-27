package com.shagui.sdc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisStateDTO;
import com.shagui.sdc.api.dto.PageableDTO;
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
	public PageableDTO<SquadDTO> squads(Integer page) {
		return rwsSdcClient.squads(page);
	}

	@Override
	public PageableDTO<SquadDTO> squads(int departmentId, Integer page) {
		return rwsSdcClient.squads(departmentId, page);
	}

	@Override
	public MetricAnalysisStateDTO squadState(int squadId) {
		return rwsSdcClient.squadState(squadId);
	}

	@Override
	public PageableDTO<ComponentDTO> squadComponents(int squadId, Integer page) {
		return rwsSdcClient.squadComponents(squadId, page);
	}

}
