package com.shagui.sdc.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;

class AnalysisServiceTest {

	@InjectMocks
	AnalysisServiceImpl service;

	@Mock
	private RwsSdcClient rwsSdcClient;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void analysisTest() {
		MetricAnalysisDTO value = new MetricAnalysisDTO();
		when(rwsSdcClient.analysis(anyInt(), anyInt())).thenReturn(value);
		MetricAnalysisDTO result = service.analysis(1, 1);

		assertEquals(result, value);
	}

	@Test
	void metricHistoryTest() {
		PageData<MetricAnalysisDTO> value = new PageData<MetricAnalysisDTO>();
		when(rwsSdcClient.metricHistory(anyInt(), anyInt())).thenReturn(value);
		PageData<MetricAnalysisDTO> result = service.metricHistory(1, 1);

		assertEquals(result, value);
	}

	@Test
	void metricNameHistoryTest() {
		PageData<MetricAnalysisDTO> value = new PageData<MetricAnalysisDTO>();
		when(rwsSdcClient.metricHistory(anyInt(), anyString(), anyString())).thenReturn(value);
		PageData<MetricAnalysisDTO> result = service.metricHistory(1, "metricName", "GIT");

		assertEquals(result, value);
	}

	@Test
	void analyzeTest() {
		PageData<MetricAnalysisDTO> value = new PageData<MetricAnalysisDTO>();
		when(rwsSdcClient.analyze(anyInt())).thenReturn(value);
		PageData<MetricAnalysisDTO> result = service.analize(1);

		assertEquals(result, value);
	}

}
