package com.shagui.sdc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.AnalysisValuesDTO;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.enums.MetricValueType;
import com.shagui.sdc.service.AnalysisService;
import com.shagui.sdc.service.ComponentService;
import com.shagui.sdc.service.MetricService;

class AnalysisControllerTest {

	@InjectMocks
	private AnalysisController controller;

	@Mock
	private AnalysisService analysisService;

	@Mock
	private ComponentService componentService;

	@Mock
	private MetricService metricService;

	@Mock
	private PageData<MetricAnalysisDTO> dto;

	@Mock
	private MetricAnalysisDTO metricAnalysisDTO;

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
	void MetricNameHistoryTest() {
		when(analysisService.metricHistory(Mockito.anyInt(), Mockito.anyString(), Mockito.any(AnalysisType.class), Mockito.any(Date.class))).thenReturn(dto);
		PageData<MetricAnalysisDTO> result = controller.metricHistory(1, "metric name",AnalysisType.GIT, new Date());
		assertNotNull(result);
	}

	@Test
	void MetricHistoryTestDateNUll() {
		when(analysisService.metricHistory(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(Date.class))).thenReturn(dto);
		PageData<MetricAnalysisDTO> result = controller.metricHistory(1, 1, null);
		assertNotNull(result);
	}

	@Test
	void analysisTest() {
		
		when(analysisService.analysis(Mockito.anyInt(), Mockito.anyInt())).thenReturn(metricAnalysisDTO);
		MetricAnalysisDTO result = controller.analysis(1,1);
		assertNotNull(result);

	}

	@Test
	void analyzeFindByTest() {

		ComponentDTO value = new ComponentDTO();
		value.setId(1);
		when(componentService.findBy(Mockito.anyInt(), Mockito.anyString())).thenReturn(value);
		when(analysisService.analyze(Mockito.anyInt())).thenReturn(dto);
		PageData<MetricAnalysisDTO> result = controller.analyze(1, "test");
		assertNotNull(result);
	}

	@Test
	void annualSumTest() {
		// Arrange
		String metricName = "metric name";
		String metricValue = "value2=2.0;value1=1.0";
		AnalysisType metricType = AnalysisType.GIT;
		Integer componentId = 1;
		Integer squadId = 2;
		Integer departmentId = 3;

		MetricDTO metric = mock(MetricDTO.class);
		when(metric.getId()).thenReturn(1);
		when(metric.getName()).thenReturn(metricName);
		when(metric.getValue()).thenReturn("metric value");
		when(metric.getDescription()).thenReturn("metric description");
		when(metric.getType()).thenReturn(metricType);
		when(metric.getValueType()).thenReturn(MetricValueType.NUMERIC_MAP);
		when(metric.getValidation()).thenReturn(null);

		when(metricService.metric(metricName, metricType)).thenReturn(metric);

		when(analysisService.filterAnalysis(anyInt(), anyInt(), anyInt(), anyInt(),
				any(Date.class))).thenReturn(new PageData<>(
						Arrays.asList(
								new MetricAnalysisDTO(new Date(), metric,
										new AnalysisValuesDTO(0, metricValue, null, null, null),
										null))));

		// Act
		PageData<MetricAnalysisDTO> result = controller.annualSum(metricName, metricType, componentId, squadId,
				departmentId);

		// Assert
		assertEquals(12, result.getPage().size());
		assertEquals(metricValue, result.getPage().get(0).getAnalysisValues().getMetricValue());
	}
}