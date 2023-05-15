package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.SquadDTO;

public interface SquadService {
	SquadDTO findById(int squadId);

	PageData<SquadDTO> findAll();

	PageData<SquadDTO> findAll(RequestPageInfo pageInfo);

	PageData<SquadDTO> findByDepartment(int department);

	PageData<SquadDTO> findByDepartment(int departmentId, RequestPageInfo pageInfo);
}