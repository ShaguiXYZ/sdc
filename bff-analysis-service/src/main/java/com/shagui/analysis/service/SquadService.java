package com.shagui.analysis.service;

import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.api.dto.SquadDTO;

public interface SquadService {
	PageableDTO<SquadDTO> squads(Integer page);	
	PageableDTO<ComponentDTO> squadComponents(int id, Integer page);	
}
