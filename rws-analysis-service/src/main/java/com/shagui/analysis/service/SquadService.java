package com.shagui.analysis.service;

import java.util.Date;

import com.shagui.analysis.api.dto.MetricAnalysisStateDTO;
import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.api.dto.SquadDTO;

public interface SquadService {
	SquadDTO findById(int squadId);

	PageableDTO<SquadDTO> findAll();

	PageableDTO<SquadDTO> findAll(int page);

	MetricAnalysisStateDTO squadState(int squadId, Date date);
}
