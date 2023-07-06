package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.repository.MetricRepository;
import com.shagui.sdc.service.impl.MetricServiceImpl;
import com.shagui.sdc.test.utils.RwsTestUtils;
import com.shagui.sdc.util.Mapper;

class MetricServiceImplTest {

	@InjectMocks
	private MetricServiceImpl service;

	@Mock
	private MetricRepository metricRepositoryMock;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		Mapper.setConfig(RwsTestUtils.mapperConfig());

	}

	@Test
	void constructorTest() {

		MetricServiceImpl service = new MetricServiceImpl(metricRepositoryMock);
		assertNotNull(service);

	}

	@Test
	void createTest() {
		MetricModel model = RwsTestUtils.metricModelMock(1, AnalysisType.GIT_XML);
		when(metricRepositoryMock.save(any(MetricModel.class))).thenReturn(RwsTestUtils.metricModelMock(1, AnalysisType.GIT_XML));
		MetricDTO result = service.create(Mapper.parse(model));
		assertNotNull(result);

	}

	@Test
	void updateNotFoundTest() {
		MetricModel model = RwsTestUtils.metricModelMock(1, AnalysisType.GIT_XML);
		assertThrows(JpaNotFoundException.class, () -> service.update(1, Mapper.parse(model)));

	}

	@Test
	void updateNotIdFoundTest() {
		MetricModel model = RwsTestUtils.metricModelMock(1, AnalysisType.GIT_XML);
		when(metricRepositoryMock.findById(any())).thenReturn(Optional.of(RwsTestUtils.metricModelMock(2, AnalysisType.GIT_XML)));
		assertThrows(JpaNotFoundException.class, () -> service.update(2, Mapper.parse(model)));
	}

	@Test
	void updateTest() {
		MetricModel model = RwsTestUtils.metricModelMock(1, AnalysisType.GIT_XML);
		when(metricRepositoryMock.findById(any())).thenReturn(Optional.of(RwsTestUtils.metricModelMock(1, AnalysisType.GIT_XML)));
		when(metricRepositoryMock.save(any(MetricModel.class))).thenReturn(RwsTestUtils.metricModelMock(1, AnalysisType.GIT_XML));
		MetricDTO result =  service.update(1, Mapper.parse(model));
		assertNotNull(result);
	}

}