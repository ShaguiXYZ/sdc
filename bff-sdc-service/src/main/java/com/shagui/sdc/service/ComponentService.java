package com.shagui.sdc.service;

import com.shagui.sdc.api.dto.ComponentHistoricalCoverageDTO;
import com.shagui.sdc.api.dto.PageableDTO;

public interface ComponentService {
	PageableDTO<ComponentHistoricalCoverageDTO> historicalCoverage(int componentId);
}
