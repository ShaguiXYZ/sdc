package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.repository.ComponentAnalysisRepository;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.MetricValueRepository;
import com.shagui.sdc.service.impl.AnalysisServiceImpl;
import com.shagui.sdc.test.utils.RwsTestUtils;
import com.shagui.sdc.util.AnalysisUtils;

class AnalysisServiceImplTest {

	@InjectMocks
	private AnalysisServiceImpl service;

	@Mock
	private ComponentRepository componentRepositoryMock;

	@Mock
	private ComponentAnalysisRepository componentAnalysisRepository;

	@Mock
	private MetricValueRepository metricValuesRepository;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		AnalysisUtils.setConfig(RwsTestUtils.analysisUtilsConfig());

	}

	@Test
	void constructorTest() {

		AnalysisServiceImpl service = new AnalysisServiceImpl(componentRepositoryMock, componentAnalysisRepository);
		assertNotNull(service);

	}

}