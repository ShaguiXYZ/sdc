package com.shagui.sdc.service;

import java.util.Date;

import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.ComponentHistoricalCoverageDTO;
import com.shagui.sdc.api.dto.MetricAnalysisStateDTO;
import com.shagui.sdc.api.dto.PageableDTO;

public interface ComponentService {
	MetricAnalysisStateDTO componentState(int componentId, Date date);

	ComponentDTO create(ComponentDTO component);

	ComponentDTO update(Integer id, ComponentDTO component);

	PageableDTO<ComponentDTO> findBySquad(int squadId, Integer page);

	PageableDTO<ComponentHistoricalCoverageDTO> historicalCoverage(int componentId);
}
