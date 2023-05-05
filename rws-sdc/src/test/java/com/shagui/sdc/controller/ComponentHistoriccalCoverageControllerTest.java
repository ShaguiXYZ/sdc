package com.shagui.sdc.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.HistoricalCoverageDTO;
import com.shagui.sdc.service.ComponentHistoricalCoverageService;

class ComponentHistoriccalCoverageControllerTest {
	
	@InjectMocks
	private ComponentHistoricalCoverageController controller;
	
	@Mock
	private ComponentHistoricalCoverageService componentHistoricalCoverageService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	
	@Test
	void HistoricalCoveragePageNullTest() {
		
		HistoricalCoverageDTO<ComponentDTO> dto = new HistoricalCoverageDTO<ComponentDTO>(null, null);
		
		when(componentHistoricalCoverageService.historicalCoverage(anyInt())).thenReturn(dto);
		HistoricalCoverageDTO<ComponentDTO> result = controller.historicalCoverage(1, null, 1);
		assertNotNull(result);
	}
	
	@Test
	void HistoricalCoveragePageNotNullTest() {
		
		HistoricalCoverageDTO<ComponentDTO> dto = new HistoricalCoverageDTO<ComponentDTO>(null, null);
		
		when(componentHistoricalCoverageService.historicalCoverage(anyInt(), any(RequestPageInfo.class))).thenReturn(dto);
		HistoricalCoverageDTO<ComponentDTO> result = controller.historicalCoverage(1, 1, 1);
		assertNotNull(result);
	}
}
