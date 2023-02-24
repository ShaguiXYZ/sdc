package com.shagui.analysis.service;

import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.PaginatedDTO;

public interface SquadService {
	PaginatedDTO<ComponentDTO> squadComponents(int id, int page);	
}
