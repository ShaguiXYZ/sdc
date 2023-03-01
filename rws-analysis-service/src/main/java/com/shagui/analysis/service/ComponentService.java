package com.shagui.analysis.service;

import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.PageableDTO;

public interface ComponentService {
	ComponentDTO create(ComponentDTO component);
	ComponentDTO update(Integer id, ComponentDTO component);
	PageableDTO<ComponentDTO> findBySquad(int squadId, int page);
}
