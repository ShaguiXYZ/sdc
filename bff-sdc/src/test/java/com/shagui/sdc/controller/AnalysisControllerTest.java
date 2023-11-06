package com.shagui.sdc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.view.MetricAnalysisView;
import com.shagui.sdc.service.AnalysisService;
import com.shagui.sdc.test.utils.DataUtils;
import com.shagui.sdc.test.utils.DtoDataUtils;

class AnalysisControllerTest {

	@InjectMocks
	AnalysisController controller;

	@Mock
	AnalysisService analysisService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void analysisTest() {
		MetricAnalysisDTO dto = DtoDataUtils.createMetricAnalysis();
		when(analysisService.analysis(Mockito.anyInt(), Mockito.anyInt())).thenReturn(dto);
		MetricAnalysisView result = controller.analysis(1, 1);
		assertNotNull(result);
		assertEquals(dto.getAnalysisDate(), result.getAnalysisDate());
		assertEquals(dto.getCoverage(), result.getCoverage());
	}

	@Test
	void metricHistoryTest() {
		when(analysisService.metricHistory(Mockito.anyInt(), Mockito.anyInt())).thenReturn(DataUtils.createEmptyPageData());
		PageData<MetricAnalysisView> result = controller.metricHistory(1, 1);
		assertNotNull(result);
	}

	@Test
	void metricNameHistoryTest() {
		when(analysisService.metricHistory(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(DataUtils.createEmptyPageData());
		PageData<MetricAnalysisView> result = controller.metricHistory(1, "metricName","GIT");
		assertNotNull(result);
	}

	@Test
	void analyzeTest() {
		when(analysisService.analize(Mockito.anyInt())).thenReturn(DataUtils.createEmptyPageData());
		PageData<MetricAnalysisView> result = controller.analyze(1);
		assertNotNull(result);
	}

	@Test
	void annualSum() {
		when(analysisService.annualSum(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(DataUtils.createEmptyPageData());
		PageData<MetricAnalysisView> result = controller.annualSum("metricName", "GIT", 1, 1, 1);
		assertNotNull(result);
	}
}
