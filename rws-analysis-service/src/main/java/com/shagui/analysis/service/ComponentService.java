package com.shagui.analysis.service;

import java.util.Date;

import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.MetricAnalysisStateDTO;
import com.shagui.analysis.api.dto.PageableDTO;

public interface ComponentService {
	MetricAnalysisStateDTO componentState(int componentId, Date date);
	ComponentDTO create(ComponentDTO component);
	ComponentDTO update(Integer id, ComponentDTO component);
	PageableDTO<ComponentDTO> findBySquad(int squadId, Integer page);
}
