package com.shagui.analysis.service;

import com.shagui.analysis.api.dto.ComponentHistoricalCoverageDTO;
import com.shagui.analysis.api.dto.PageableDTO;

public interface ComponentService {
	PageableDTO<ComponentHistoricalCoverageDTO> historicalCoverage(int componentId);
}
