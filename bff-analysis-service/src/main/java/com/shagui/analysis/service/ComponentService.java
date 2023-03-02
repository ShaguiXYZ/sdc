package com.shagui.analysis.service;

import com.shagui.analysis.api.dto.MetricAnalysisStateDTO;

public interface ComponentService {
	MetricAnalysisStateDTO componentState(int componentId);
}
