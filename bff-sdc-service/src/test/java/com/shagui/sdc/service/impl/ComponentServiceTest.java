package com.shagui.sdc.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.client.RwsSdcClient;
import com.shagui.sdc.api.domain.HistoricalCoverage;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricDTO;

class ComponentServiceTest {

	@InjectMocks
	ComponenServiceImpl service;

	@Mock
	private RwsSdcClient rwsSdcClient;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void findByTest() {

		ComponentDTO value = new ComponentDTO();
		when(rwsSdcClient.component(anyInt())).thenReturn(value);
		ComponentDTO result = service.findBy(1);

		assertEquals(result, value);

	}

	@Test
	void filterTest() {

		PageData<ComponentDTO> value = new PageData<ComponentDTO>();
		when(rwsSdcClient.filter(anyString(), anyInt(), any(Float.class), any(Float.class), anyInt(), anyInt()))
				.thenReturn(value);
		PageData<ComponentDTO> result = service.filter("test", 1, (float) 90.1, (float) 10.1, 1, 1);

		assertEquals(result, value);

	}

	@Test
	void componentMetricsTest() {

		PageData<MetricDTO> value = new PageData<MetricDTO>();
		when(rwsSdcClient.componentMetrics(anyInt())).thenReturn(value);
		PageData<MetricDTO> result = service.componentMetrics(1);

		assertEquals(result, value);

	}

	@Test
	void historicalCoverageTest() {

		HistoricalCoverage<ComponentDTO> value = new HistoricalCoverage<ComponentDTO>();
		when(rwsSdcClient.componentHistoricalCoverage(anyInt(), anyInt(), anyInt())).thenReturn(value);
		HistoricalCoverage<ComponentDTO> result = service.historical(1, 1, 1);

		assertEquals(result, value);

	}

}
