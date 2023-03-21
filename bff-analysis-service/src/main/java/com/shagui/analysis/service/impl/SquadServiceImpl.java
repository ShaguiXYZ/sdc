package com.shagui.analysis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.analysis.api.client.RwsSdcClient;
import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.MetricAnalysisStateDTO;
import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.api.dto.SquadDTO;
import com.shagui.analysis.service.SquadService;

@Service
public class SquadServiceImpl implements SquadService {

	@Autowired
	private RwsSdcClient rwsSdcClient;

	@Override
	public SquadDTO squad(int squadId) {
		return rwsSdcClient.squad(squadId);
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
