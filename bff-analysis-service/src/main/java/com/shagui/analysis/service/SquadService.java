package com.shagui.analysis.service;

import com.shagui.analysis.api.dto.ComponentsDTO;

public interface SquadService {
	ComponentsDTO squadComponents(int id, int page);	
}
