package com.shagui.sdc.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.ComponentHistoricalCoverageApi;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.HistoricalCoverageDTO;
import com.shagui.sdc.service.ComponentHistoricalCoverageService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "historical", description = "API to retirieve historical coverages")
public class ComponentHistoricalCoverageController implements ComponentHistoricalCoverageApi {
	private ComponentHistoricalCoverageService componentHistoricalCoverageService;

	@Override
	public HistoricalCoverageDTO<ComponentDTO> historicalCoverage(int componentId, Integer page, Integer ps) {
		if (page == null) {
			return componentHistoricalCoverageService.historicalCoverage(componentId);
		} else {
			return componentHistoricalCoverageService.historicalCoverage(componentId, new RequestPageInfo(page, ps));
		}
	}

}
