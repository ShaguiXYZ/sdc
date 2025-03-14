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
import com.shagui.sdc.api.dto.MetricValuesDTO;
import com.shagui.sdc.api.dto.MetricValuesOutDTO;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.MetricValuesModel;
import com.shagui.sdc.repository.ComponentTypeArchitectureMetricPropertiesRepository;
import com.shagui.sdc.repository.ComponentTypeArchitectureRepository;
import com.shagui.sdc.repository.MetricRepository;
import com.shagui.sdc.repository.MetricValueRepository;
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

	@Mock
	private MetricValueRepository metricValueRepositoryMock;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void componentTypeMetricsTest() {
		MetricModel metricMock = RwsTestUtils.metricModelMock(1, AnalysisType.GIT, "metric name 1", "metric name 1");

		when(metricRepositoryMock.findByNameIgnoreCaseAndType(anyString(), any(AnalysisType.class))).thenReturn(Optional.of(metricMock));
		when(componentTypeArchitectureRepositoryMock.findByComponentTypeAndArchitecture(anyString(), anyString())).thenReturn(new ArrayList<>() {
			private static final long serialVersionUID = 1L;

			{
				add(RwsTestUtils.componentTypeArchitectureModelMock());
			}}
		);
		when(componentTypeArchitectureRepositoryMock.save(any(ComponentTypeArchitectureModel.class))).thenReturn(RwsTestUtils.componentTypeArchitectureModelMock());
		when(componentTypeArchitectureMetricPropertiesRepository.findByComponentTypeArchitectureAndMetricAndNameIgnoreCase(any(ComponentTypeArchitectureModel.class), any(MetricModel.class), anyString())).thenReturn(Optional.of(RwsTestUtils.componetTypeArchitectureMetricPropertiesModelMock()));

		List<ComponentTypeArchitectureDTO> result = service.componentTypeArchitectureMetrics("component-type-name", "architecture-name", new ArrayList<MetricPropertiesDTO>() {
			private static final long serialVersionUID = 1L;

			{
				MetricPropertiesDTO data1 = new MetricPropertiesDTO();
				data1.setMetricName(metricMock.getName());
				data1.setType(metricMock.getType());
				data1.setParams(new HashMap<>() {
					private static final long serialVersionUID = 1L;
	
					{
						put("param1", "value1");
					}}
				);
				add(data1);
			}}
		);
		
		assertNotNull(result);
	}

	@Test
	void componentTypeMetricsNonPropertyPresentTest() {
		MetricModel metricMock = RwsTestUtils.metricModelMock(1, AnalysisType.GIT, "metric name 1", "metric name 1");
		
		when(metricRepositoryMock.findByNameIgnoreCaseAndType(anyString(), any(AnalysisType.class))).thenReturn(Optional.of(metricMock));
		when(componentTypeArchitectureRepositoryMock.findByComponentTypeAndArchitecture(anyString(), anyString())).thenReturn(new ArrayList<>() {
			private static final long serialVersionUID = 1L;

			{
				add(RwsTestUtils.componentTypeArchitectureModelMock());
			}}
		);
		when(componentTypeArchitectureRepositoryMock.save(any(ComponentTypeArchitectureModel.class))).thenReturn(RwsTestUtils.componentTypeArchitectureModelMock());
		when(componentTypeArchitectureMetricPropertiesRepository.findByComponentTypeArchitectureAndMetricAndNameIgnoreCase(any(ComponentTypeArchitectureModel.class), any(MetricModel.class), anyString())).thenReturn(Optional.empty());

		List<ComponentTypeArchitectureDTO> result = service.componentTypeArchitectureMetrics("component-type-name", "architecture-name", new ArrayList<MetricPropertiesDTO>() {
			private static final long serialVersionUID = 1L;

			{
				MetricPropertiesDTO data1 = new MetricPropertiesDTO();
				data1.setMetricName(metricMock.getName());
				data1.setType(metricMock.getType());
				data1.setParams(new HashMap<>());
				add(data1);
			}}
		);
		
		assertNotNull(result);
	}

	@Test
	void defineMetricValuesTest() {
		when(metricRepositoryMock.findByNameIgnoreCaseAndType(anyString(), any(AnalysisType.class))).thenReturn(Optional.of(RwsTestUtils.metricModelMock(1, AnalysisType.GIT_XML, "metric name 1", "git metric")));
		when(componentTypeArchitectureRepositoryMock.findByComponentTypeAndArchitecture(anyString(), anyString())).thenReturn(new ArrayList<>() {
			private static final long serialVersionUID = 1L;

			{
				add(RwsTestUtils.componentTypeArchitectureModelMock());
			}}
		);
		when(metricValueRepositoryMock.save(any(MetricValuesModel.class))).thenReturn(RwsTestUtils.metricValuesModelMock());
		
		List<MetricValuesOutDTO> result = service.defineMetricValues("componentType", "architecture", "metricName", AnalysisType.GIT, new MetricValuesDTO() {{
			setGoodValue("1");
		}});

		assertNotNull(result);		
	}
}
