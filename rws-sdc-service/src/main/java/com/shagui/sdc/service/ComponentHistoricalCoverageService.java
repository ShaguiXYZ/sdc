package com.shagui.sdc.service;

import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.HistoricalCoverageDTO;

public interface ComponentHistoricalCoverageService {
	public HistoricalCoverageDTO<ComponentDTO> historicalCoverage(int componentId);

	public HistoricalCoverageDTO<ComponentDTO> historicalCoverage(int componentId, RequestPageInfo pageInfo);
}
