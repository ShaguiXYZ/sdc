package com.shagui.sdc.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import com.shagui.sdc.core.configuration.DictionaryConfig;
import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.repository.ComponentAnalysisRepository;
import com.shagui.sdc.repository.ComponentHistoricalCoverageRepository;
import com.shagui.sdc.repository.ComponentPropertyRepository;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.ComponentTagRepository;
import com.shagui.sdc.repository.DepartmentRepository;
import com.shagui.sdc.repository.MetricValueRepository;
import com.shagui.sdc.repository.SquadRepository;
import com.shagui.sdc.repository.TagRepository;
import com.shagui.sdc.test.utils.RwsTestUtils;

class ComponentUtilsTest {

	@Mock
	private static DictionaryConfig securityTokenConfig;

	@Mock
	private static ComponentRepository componentRepository;

	@Mock
	private static ComponentAnalysisRepository componentAnalysisRepository;

	@Mock
	private static ComponentHistoricalCoverageRepository componentHistoricalCoverageRepository;

	@Mock
	private static ComponentPropertyRepository componentPropertyRepository;

	@Mock
	private static ComponentTagRepository componentTagRepository;

	@Mock
	private static DepartmentRepository departmentRepository;

	@Mock
	private static SquadRepository squadRepository;

	@Mock
	private static MetricValueRepository metricValueRepository;

	@Mock
	private static TagRepository tagRepository;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		ComponentUtils.setConfig(
				new ComponentUtilsConfig(securityTokenConfig, componentRepository, componentAnalysisRepository,
						componentHistoricalCoverageRepository, componentPropertyRepository,
						componentTagRepository, departmentRepository, squadRepository, tagRepository));
		AnalysisUtils.setConfig(new AnalysisUtilsConfig(metricValueRepository));
	}

	@Test
	void createComponentPropertiesTest() {
		when(componentPropertyRepository.save(any(ComponentPropertyModel.class)))
				.thenReturn(RwsTestUtils.componentProperty("property_name"));
		ComponentUtils.addOrUpdatePropertyValue(RwsTestUtils.componentModelMock(), "property_name", "property_value");

		verify(componentPropertyRepository, times(1)).save(any(ComponentPropertyModel.class));
	}

	@Test
	void updateRelatedComponentEntities() {
		when(componentAnalysisRepository.componentAnalysis(anyInt(), any(Timestamp.class))).thenReturn(
				new ArrayList<>() {
					private static final long serialVersionUID = 1L;

					{
						add(RwsTestUtils.componentAnalysisModelMock("12.0"));
					}
				});
		when(metricValueRepository.metricValuesByDate(anyInt(), anyInt(), any(Timestamp.class))).thenReturn(
				new ArrayList<>() {
					private static final long serialVersionUID = 1L;

					{
						add(RwsTestUtils.metricValuesModelMock());
					}
				});
		when(componentHistoricalCoverageRepository.save(any(ComponentHistoricalCoverageModel.class)))
				.thenReturn(new ComponentHistoricalCoverageModel());
		when(componentRepository.findById(anyInt())).thenReturn(Optional.of(RwsTestUtils.componentModelMock()));
		when(componentRepository.save(any(ComponentModel.class))).thenReturn(RwsTestUtils.componentModelMock());
		when(squadRepository.findById(anyInt())).thenReturn(Optional.of(RwsTestUtils.squadModelMock()));
		when(squadRepository.save(any(SquadModel.class))).thenReturn(RwsTestUtils.squadModelMock());

		ComponentUtils.updateRelatedComponentEntities(RwsTestUtils.componentModelMock(), true);
		verify(componentRepository, times(1)).save(any(ComponentModel.class));
		verify(componentHistoricalCoverageRepository, times(1)).save(any(ComponentHistoricalCoverageModel.class));
		verify(squadRepository, times(1)).save(any(SquadModel.class));

	}

}
