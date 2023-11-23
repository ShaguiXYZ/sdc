package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.repository.ComponentAnalysisRepository;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.MetricRepository;
import com.shagui.sdc.repository.MetricValueRepository;
import com.shagui.sdc.service.impl.AnalysisServiceImpl;
import com.shagui.sdc.test.utils.RwsTestUtils;
import com.shagui.sdc.util.AnalysisUtils;
import com.shagui.sdc.util.AnalysisUtilsConfig;

class AnalysisServiceImplTest {

	@InjectMocks
	private AnalysisServiceImpl service;

	@Mock
	private ComponentRepository componentRepositoryMock;

	@Mock
	private ComponentAnalysisRepository componentAnalysisRepositoryMock;

	@Mock
	private MetricRepository metricRepositoryMock;

	@Mock
	private MetricValueRepository metricValueRepositoryMock;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		AnalysisUtils.setConfig(new AnalysisUtilsConfig(metricValueRepositoryMock));
	}

	@Test
	void constructorTest() {
		AnalysisServiceImpl service = new AnalysisServiceImpl(new HashMap<>(), componentRepositoryMock,
				componentAnalysisRepositoryMock,
				metricRepositoryMock);
		assertNotNull(service);
	}

	@Test
	void analysisTest() {
		// given
		int componentId = 1;
		List<ComponentAnalysisModel> model = new ArrayList<>();
		model.add(RwsTestUtils.componentAnalysisModelMock("metric value"));
		when(componentAnalysisRepositoryMock.componentAnalysis(anyInt(), any(Timestamp.class)))
				.thenReturn(model);
		when(metricValueRepositoryMock.metricValueByDate(anyInt(), anyInt(), any(Timestamp.class)))
				.thenReturn(new ArrayList<>() {
					{
						add(RwsTestUtils.metricValuesModelMock());
					}
				});

		// when
		List<MetricAnalysisDTO> result = service.analysis(componentId).getPage();

		// then
		assertEquals(1, result.size());
	}

	@Test
	void analysisWithMetricIdNotFoundTest() {
		// given
		int componentId = 1;
		int metricId = 2;
		when(componentAnalysisRepositoryMock.actualMetric(anyInt(), anyInt()))
				.thenReturn(Optional.empty());

		// then
		assertThrows(JpaNotFoundException.class, () -> service.analysis(componentId, metricId));
	}

	@Test
	void metricHistoryTest() {
		// given
		int componentId = 1;
		int metricId = 1;
		Date date = new Date();
		List<ComponentAnalysisModel> model = new ArrayList<>();
		model.add(RwsTestUtils.componentAnalysisModelMock("metric value"));
		when(componentAnalysisRepositoryMock.historyOfMetricByDate(anyInt(), anyInt(), any(Timestamp.class)))
				.thenReturn(model);

		// when
		List<MetricAnalysisDTO> result = service.metricHistory(componentId, metricId, date).getPage();

		// then
		assertEquals(1, result.size());
	}

	@Test
	void metricHistoryWithNameTest() {
		// given
		int componentId = 1;
		String metricName = "metric1";
		AnalysisType type = AnalysisType.GIT;
		Date date = new Date();
		List<ComponentAnalysisModel> model = new ArrayList<>();
		model.add(RwsTestUtils.componentAnalysisModelMock("metric value"));
		when(metricRepositoryMock.findByNameIgnoreCaseAndType(metricName, type))
				.thenReturn(Optional.of(RwsTestUtils.metricModelMock(1, type, metricName, "metric value")));
		when(componentAnalysisRepositoryMock.historyOfMetricByDate(anyInt(), anyInt(), any(Timestamp.class)))
				.thenReturn(model);

		// when
		List<MetricAnalysisDTO> result = service.metricHistory(componentId, metricName, type, date).getPage();

		// then
		assertEquals(1, result.size());
	}
}