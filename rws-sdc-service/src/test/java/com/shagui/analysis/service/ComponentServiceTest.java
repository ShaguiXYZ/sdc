package com.shagui.analysis.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.dto.MetricAnalysisStateDTO;
import com.shagui.sdc.repository.ComponentAnalysisRepository;
import com.shagui.sdc.repository.ComponentHistoricalCoverageRepository;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.ComponentTypeArchitectureRepository;
import com.shagui.sdc.service.impl.ComponentServiceImpl;

class ComponentServiceTest {

	@InjectMocks
	ComponentServiceImpl service;

	@Mock
	ComponentRepository componentsRepository;

	@Mock
	ComponentTypeArchitectureRepository componentTypeArchitectureRepository;

	@Mock
	ComponentAnalysisRepository componentAnalysisRepository;

	@Mock
	ComponentHistoricalCoverageRepository historicalCoverageComponentRepository;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void componentServiceConstructorTest() {

		ComponentServiceImpl service = new ComponentServiceImpl(componentsRepository,
				componentTypeArchitectureRepository, componentAnalysisRepository,
				historicalCoverageComponentRepository);

		assertNotNull(service);
	}

	@Test
	void componentState() {
		MetricAnalysisStateDTO componentState = service.componentState(1, new Date());
		assertNotNull(componentState);
	}

}
