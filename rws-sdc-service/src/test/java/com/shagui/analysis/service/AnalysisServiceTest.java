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

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.repository.ComponentAnalysisRepository;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.MetricValueRepository;
import com.shagui.sdc.service.impl.AnalysisServiceImpl;

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

		AnalysisServiceImpl service = new AnalysisServiceImpl(componentsRepository, componentAnalysisRepository);

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
		PageData<MetricAnalysisDTO> analyze = service.analyze(1);
		assertNotNull(analyze);
	}

	@Test
	void metricHistoryTest() {
		PageData<MetricAnalysisDTO> metricHistory = service.metricHistory(1, 1, new Date());
		assertNotNull(metricHistory);
	}

}
