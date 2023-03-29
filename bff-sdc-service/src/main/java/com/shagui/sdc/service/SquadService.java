package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.SquadDTO;

public interface SquadService {
	SquadDTO squad(int squadId);

	PageData<SquadDTO> squads(Integer page);

	PageData<SquadDTO> squads(int departmentId, Integer page);

	PageData<ComponentDTO> squadComponents(int id, Integer page, Integer ps);
}
