package com.shagui.sdc.service;

import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisStateDTO;
import com.shagui.sdc.api.dto.PageableDTO;
import com.shagui.sdc.api.dto.SquadDTO;

public interface SquadService {
	SquadDTO squad(int squadId);

	PageableDTO<SquadDTO> squads(Integer page);

	PageableDTO<SquadDTO> squads(int departmentId, Integer page);

	MetricAnalysisStateDTO squadState(int squadId);

	PageableDTO<ComponentDTO> squadComponents(int id, Integer page);
}
