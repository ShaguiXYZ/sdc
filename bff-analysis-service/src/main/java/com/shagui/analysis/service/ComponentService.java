package com.shagui.analysis.service;

import com.shagui.analysis.api.dto.ComponentStateDTO;

public interface ComponentService {
	ComponentStateDTO componentState(int componentId);
}
