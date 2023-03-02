package com.shagui.analysis.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.analysis.api.dto.MetricAnalysisStateDTO;
import com.shagui.analysis.repository.ComponentAnalysisRepository;
import com.shagui.analysis.repository.ComponentRepository;
import com.shagui.analysis.repository.ComponentTypeArchitectureRepository;
import com.shagui.analysis.service.impl.ComponentServiceImpl;

class ComponentServiceTest {

	@InjectMocks
	ComponentServiceImpl service;

	@Mock
	ComponentRepository componentsRepository;

	@Mock
	ComponentTypeArchitectureRepository componentTypeArchitectureRepository;

	@Mock
	ComponentAnalysisRepository componentAnalysisRepository;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void componentServiceConstructorTest() {

		ComponentServiceImpl service = new ComponentServiceImpl(componentsRepository,
				componentTypeArchitectureRepository, componentAnalysisRepository);

		assertNotNull(service);
	}

	@Test
	void componentState() {
		MetricAnalysisStateDTO componentState = service.componentState(1, new Date());
		assertNotNull(componentState);
	}

}
