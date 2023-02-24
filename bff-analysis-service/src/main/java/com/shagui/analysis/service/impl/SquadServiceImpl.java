package com.shagui.analysis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.analysis.api.client.RwsSdcClient;
import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.PaginatedDTO;
import com.shagui.analysis.service.SquadService;

@Service
public class SquadServiceImpl implements SquadService {

	@Autowired
	private RwsSdcClient rwsSdcClient;

	@Override
	public PaginatedDTO<ComponentDTO> squadComponents(int squadId, int page) {
		return rwsSdcClient.squadComponents(squadId, page);
	}

}
