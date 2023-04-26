package com.shagui.sdc.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.service.AnalysisService;

class AnalysisControllerTest {

	@InjectMocks
	AnalysisController controller;

	@Mock
	AnalysisService analysisService;

	@Mock
	PageData<MetricAnalysisDTO> dto;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void analyzeComponentTest() {
		when(analysisService.analyze(Mockito.anyInt())).thenReturn(dto);
		PageData<MetricAnalysisDTO> result = controller.analyze(1);
		assertNotNull(result);
	}

	@Test
	void MetricHistoryTest() {
		when(analysisService.metricHistory(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(Date.class))).thenReturn(dto);
		PageData<MetricAnalysisDTO> result = controller.metricHistory(1, 1, new Date());
		assertNotNull(result);
	}

	@Test
	void MetricHistoryTestDateNUll() {
		when(analysisService.metricHistory(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(Date.class))).thenReturn(dto);
		PageData<MetricAnalysisDTO> result = controller.metricHistory(1, 1, null);
		assertNotNull(result);
	}

}