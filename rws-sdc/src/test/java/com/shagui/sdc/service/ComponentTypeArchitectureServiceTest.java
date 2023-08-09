package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.dto.ComponentTypeArchitectureDTO;
import com.shagui.sdc.api.dto.MetricPropertiesDTO;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.repository.ComponentTypeArchitectureMetricPropertiesRepository;
import com.shagui.sdc.repository.ComponentTypeArchitectureRepository;
import com.shagui.sdc.repository.MetricRepository;
import com.shagui.sdc.service.impl.ComponentTypeArchitectureServiceImpl;
import com.shagui.sdc.test.utils.RwsTestUtils;

class ComponentTypeArchitectureServiceTest {

	@InjectMocks
	private ComponentTypeArchitectureServiceImpl service;

	@Mock
	private ComponentTypeArchitectureRepository componentTypeArchitectureRepositoryMock;

	@Mock
	private ComponentTypeArchitectureMetricPropertiesRepository componentTypeArchitectureMetricPropertiesRepository;

	@Mock
	private MetricRepository metricRepositoryMock;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void componentTypeMetricsTest() {
		when(metricRepositoryMock.findByName(anyString())).thenReturn(Optional.of(RwsTestUtils.metricModelMock(1, AnalysisType.GIT_XML, "git metric")));
		when(componentTypeArchitectureRepositoryMock.findByComponentTypeAndArchitecture(anyString(), anyString())).thenReturn(new ArrayList<>() {
			private static final long serialVersionUID = 1L;

			{
				add(RwsTestUtils.componentTypeArchitectureModelMock());
			}}
		);
		when(componentTypeArchitectureRepositoryMock.save(any(ComponentTypeArchitectureModel.class))).thenReturn(RwsTestUtils.componentTypeArchitectureModelMock());
		when(componentTypeArchitectureMetricPropertiesRepository.findByComponentTypeArchitectureAndMetricAndName(any(ComponentTypeArchitectureModel.class), any(MetricModel.class), anyString())).thenReturn(Optional.of(RwsTestUtils.componetTypeArchitectureMetricPropertiesModelMock()));

		List<ComponentTypeArchitectureDTO> result = service.componentTypeMetrics("component-type-name", "architecture-name", new HashMap<String, MetricPropertiesDTO>() {
			private static final long serialVersionUID = 1L;

			{
				MetricPropertiesDTO data1 = new MetricPropertiesDTO();
				data1.setParams(new HashMap<>() {
					private static final long serialVersionUID = 1L;
	
					{
						put("param1", "value1");
					}}
				);
				put("metric1", data1);
				
				MetricPropertiesDTO data2 = new MetricPropertiesDTO();
				put("metrec2", data2);
			}}
		);
		
		assertNotNull(result);
	}

	@Test
	void componentTypeMetricsNonPropertyPresentTest() {
		when(metricRepositoryMock.findByName(anyString())).thenReturn(Optional.of(RwsTestUtils.metricModelMock(1, AnalysisType.GIT_XML, "git metric")));
		when(componentTypeArchitectureRepositoryMock.findByComponentTypeAndArchitecture(anyString(), anyString())).thenReturn(new ArrayList<>() {
			private static final long serialVersionUID = 1L;

			{
				add(RwsTestUtils.componentTypeArchitectureModelMock());
			}}
		);
		when(componentTypeArchitectureRepositoryMock.save(any(ComponentTypeArchitectureModel.class))).thenReturn(RwsTestUtils.componentTypeArchitectureModelMock());
		when(componentTypeArchitectureMetricPropertiesRepository.findByComponentTypeArchitectureAndMetricAndName(any(ComponentTypeArchitectureModel.class), any(MetricModel.class), anyString())).thenReturn(Optional.empty());

		List<ComponentTypeArchitectureDTO> result = service.componentTypeMetrics("component-type-name", "architecture-name", new HashMap<String, MetricPropertiesDTO>() {
			private static final long serialVersionUID = 1L;

			{
				MetricPropertiesDTO data1 = new MetricPropertiesDTO();
				data1.setParams(new HashMap<>() {
					private static final long serialVersionUID = 1L;
	
					{
						put("param1", "value1");
					}}
				);
				put("metric1", data1);
				
				MetricPropertiesDTO data2 = new MetricPropertiesDTO();
				put("metrec2", data2);
			}}
		);
		
		assertNotNull(result);
	}

}
