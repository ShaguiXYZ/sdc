package com.shagui.analysis.service;

import com.shagui.analysis.api.dto.ComponentDTO;

public interface ComponentService {
	ComponentDTO create(ComponentDTO component);
	ComponentDTO update(Integer id, ComponentDTO component);
}
