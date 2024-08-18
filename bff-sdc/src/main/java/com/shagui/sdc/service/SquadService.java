package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.SquadDTO;

public interface SquadService {
	SquadDTO squad(int squadId);

	PageData<SquadDTO> squads(Integer page);

	PageData<SquadDTO> squadsByDepartment(int departmentId, Integer page);

	PageData<SquadDTO> squadsByCompany(int companyId, Integer page);

	Long countWithCoverage();
}
