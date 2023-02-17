package com.shagui.analysis.service;

import java.util.List;

import com.shagui.analysis.api.dto.ComponentDTO;

public interface SquadService {
	List<ComponentDTO> squadComponents(int id);	
}
