package com.shagui.analysis.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

}
