package com.shagui.analysis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.analysis.api.client.RwsSdcClient;
import com.shagui.analysis.api.dto.ComponentsDTO;
import com.shagui.analysis.service.SquadService;

@Service
public class SquadServiceImpl implements SquadService {

	@Autowired
	private RwsSdcClient rwsSdcClient;

	@Override
	public ComponentsDTO squadComponents(int squadId, int page) {
		return rwsSdcClient.squadComponents(squadId, page);
	}

}
