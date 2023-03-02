package com.shagui.analysis.service;

import java.util.Date;

import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.ComponentStateDTO;
import com.shagui.analysis.api.dto.PageableDTO;

public interface ComponentService {
	ComponentStateDTO componentState(int componentId, Date date);
	ComponentDTO create(ComponentDTO component);
	ComponentDTO update(Integer id, ComponentDTO component);
	PageableDTO<ComponentDTO> findBySquad(int squadId, int page);
}
