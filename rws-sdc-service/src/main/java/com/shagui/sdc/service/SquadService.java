package com.shagui.sdc.service;

import java.util.Date;

import com.shagui.sdc.api.dto.MetricAnalysisStateDTO;
import com.shagui.sdc.api.dto.PageableDTO;
import com.shagui.sdc.api.dto.SquadDTO;

public interface SquadService {
	SquadDTO findById(int squadId);

	PageableDTO<SquadDTO> findByDepartment(int department);

	PageableDTO<SquadDTO> findByDepartment(int departmentId, int page);

	MetricAnalysisStateDTO squadState(int squadId, Date date);
}
