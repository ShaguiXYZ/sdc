package com.shagui.analysis.service;

import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.ComponentsDTO;

public interface ComponentService {
	ComponentDTO create(ComponentDTO component);
	ComponentDTO update(Integer id, ComponentDTO component);
	ComponentsDTO findBySquad(int squadId, int page);
}
