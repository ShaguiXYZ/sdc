package com.shagui.analysis.service;

import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.MetricAnalysisStateDTO;
import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.api.dto.SquadDTO;

public interface SquadService {
	SquadDTO squad(int squadId);

	PageableDTO<SquadDTO> squads(int departmentId, Integer page);

	MetricAnalysisStateDTO squadState(int squadId);

	PageableDTO<ComponentDTO> squadComponents(int id, Integer page);
}
