package com.shagui.sdc.api.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.SETTER;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.PageInfo;
import com.shagui.sdc.model.MetricValuesModel;
import com.shagui.sdc.test.utils.RwsTestUtils;

@DisplayName("Tests for DTOs")
class PojoTest {

	@Test
	@DisplayName("Tests for DTOs to ensure POJO methods are well implemented")
	void testDTO() {
		assertPojoMethodsFor(AnalysisValuesDTO.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(ComponentDTO.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(ComponentTypeArchitectureDTO.class).testing(CONSTRUCTOR, GETTER, SETTER)
				.areWellImplemented();
		assertPojoMethodsFor(MetricAnalysisDTO.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(MetricDTO.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(MetricValuesDTO.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(MetricValuesOutDTO.class).testing(GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(PageData.class).testing(CONSTRUCTOR, GETTER).areWellImplemented();
		assertPojoMethodsFor(PageInfo.class).testing(GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(SquadDTO.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(DepartmentDTO.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(HistoricalCoverageDTO.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(TimeCoverageDTO.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(UriDTO.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
	}

	@Test
	void metricValuesDTO_form_MetricValuesModel_constructorTest() {
		MetricValuesModel source = RwsTestUtils.metricValuesModelMock();
		MetricValuesDTO target = new MetricValuesDTO(source);

		assertEquals(source.getValue(), target.getValue());
		assertEquals(source.getGoodValue(), target.getGoodValue());
		assertEquals(source.getPerfectValue(), target.getPerfectValue());
		assertEquals(source.getWeight(), target.getWeight());
	}

	@Test
	void metricValuesOutDTO_form_MetricValuesModel_constructorTest() {
		MetricValuesModel source = RwsTestUtils.metricValuesModelMock();
		MetricValuesOutDTO target = new MetricValuesOutDTO(source);

		assertEquals(source.getValue(), target.getValue());
		assertEquals(source.getGoodValue(), target.getGoodValue());
		assertEquals(source.getPerfectValue(), target.getPerfectValue());
		assertEquals(source.getWeight(), target.getWeight());
		assertEquals(source.getMetric().getId(), target.getMetric().getId());
	}

}