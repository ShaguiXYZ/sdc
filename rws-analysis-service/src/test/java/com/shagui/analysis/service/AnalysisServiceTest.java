package com.shagui.analysis.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.model.ComponentModel;
import com.shagui.analysis.model.ComponentTypeArchitectureModel;
import com.shagui.analysis.model.MetricModel;
import com.shagui.analysis.repository.ComponentAnalysisRepository;
import com.shagui.analysis.repository.ComponentRepository;
import com.shagui.analysis.repository.MetricValueRepository;
import com.shagui.analysis.service.impl.AnalysisServiceImpl;

class AnalysisServiceTest {

	@InjectMocks
	AnalysisServiceImpl service;

	@Mock
	ComponentRepository componentsRepository;

	@Mock
	ComponentAnalysisRepository componentAnalysisRepository;

	@Mock
	MetricValueRepository metricValuesRepository;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void analysisServiceConstructorTest() {

		AnalysisServiceImpl service = new AnalysisServiceImpl(componentsRepository, componentAnalysisRepository,
				metricValuesRepository);

		assertNotNull(service);
	}

	@Test
	void analyzeTest() {
		ComponentTypeArchitectureModel componentTypeArchitecture = new ComponentTypeArchitectureModel();
		List<MetricModel> metrics = new ArrayList<MetricModel>();
		MetricModel metricModel1 = new MetricModel();
		MetricModel metricModel2 = new MetricModel();
		metricModel1.setId(1);
		metricModel1.setId(2);
		metrics.add(metricModel1);
		metrics.add(metricModel2);
		componentTypeArchitecture.setMetrics(metrics);

		ComponentModel componentModel = new ComponentModel();
		componentModel.setId(1);
		componentModel.setComponentTypeArchitecture(componentTypeArchitecture);
		Optional<ComponentModel> opComponentModel = Optional.of(componentModel);

		when(componentsRepository.findById(any())).thenReturn(opComponentModel);
		PageableDTO<MetricAnalysisDTO> analyze = service.analyze(1);
		assertNotNull(analyze);
	}

	@Test
	void metricHistoryTest() {
		PageableDTO<MetricAnalysisDTO> metricHistory = service.metricHistory(1, 1, new Date());
		assertNotNull(metricHistory);
	}

	@Test
	void componentState() {
		PageableDTO<MetricAnalysisDTO> componentState = service.componentState(1, new Date());
		assertNotNull(componentState);
	}

}
