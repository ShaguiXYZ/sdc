package com.shagui.analysis.service;

import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.api.dto.SquadDTO;

public interface SquadService {
	PageableDTO<SquadDTO> findAll();
	PageableDTO<SquadDTO> findAll(int page);
}
