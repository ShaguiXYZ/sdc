package com.shagui.sdc.api.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;
import static pl.pojo.tester.api.assertion.Method.SETTER;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shagui.sdc.api.view.AnalysisValuesView;
import com.shagui.sdc.api.view.ComponentView;
import com.shagui.sdc.api.view.DepartmentView;
import com.shagui.sdc.api.view.MetricAnalysisView;
import com.shagui.sdc.api.view.MetricView;
import com.shagui.sdc.api.view.SquadView;
import com.shagui.sdc.test.utils.ViewDataUtils;

class PojoDtoTest {

	@Test
	@DisplayName("Tests for DTOs to ensure POJO methods are well implemented")
	void testDTO() {
		assertPojoMethodsFor(AnalysisValuesDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS)
				.areWellImplemented();
		assertPojoMethodsFor(ComponentDTO.class).testing(GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS)
				.areWellImplemented();
		assertPojoMethodsFor(MetricAnalysisDTO.class).testing(GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS)
				.areWellImplemented();
		assertPojoMethodsFor(MetricDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS)
				.areWellImplemented();
		assertPojoMethodsFor(SquadDTO.class).testing(GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
	}

	@Test
	void analysisValuesDTO_form_AnalysisValuesView_constructorTest() {
		AnalysisValuesView source = ViewDataUtils.createAnalysisValues();
		AnalysisValuesDTO target = new AnalysisValuesDTO(source);

		assertEquals(source.getMetricValue(), target.getMetricValue());
		assertEquals(source.getExpectedValue(), target.getExpectedValue());
		assertEquals(source.getGoodValue(), target.getGoodValue());
		assertEquals(source.getPerfectValue(), target.getPerfectValue());
	}

	@Test
	void componentDTO_form_ComponentView_constructorTest() {
		ComponentView source = ViewDataUtils.createComponent();
		source.setSquad(ViewDataUtils.createSquad());
		ComponentDTO target = new ComponentDTO(source);

		assertEquals(source.getId(), target.getId());
		assertEquals(source.getName(), target.getName());
		assertEquals(source.getAnalysisDate(), target.getAnalysisDate());
	}

	@Test
	void metricDTO_form_MetricView_constructorTest() {
		MetricView source = ViewDataUtils.createMetric(null, null, null);
		MetricDTO target = new MetricDTO(source);

		assertEquals(source.getId(), target.getId());
		assertEquals(source.getName(), target.getName());
	}

	@Test
	void metricAnalysisDTO_form_MetricAnalysisView_constructorTest() {
		MetricAnalysisView source = ViewDataUtils.createMetricAnalysis();
		source.setMetric(ViewDataUtils.createMetric(null, null, null));
		source.setAnalysisValues(ViewDataUtils.createAnalysisValues());
		MetricAnalysisDTO target = new MetricAnalysisDTO(source);

		assertEquals(source.getAnalysisDate(), target.getAnalysisDate());
		assertEquals(source.getCoverage(), target.getCoverage());
	}

	@Test
	void departmentDTO_form_DepartmentView_constructorTest() {
		DepartmentView source = ViewDataUtils.createDepartment();
		DepartmentDTO target = new DepartmentDTO(source);

		assertEquals(source.getId(), target.getId());
		assertEquals(source.getName(), target.getName());
		assertEquals(source.getCoverage(), target.getCoverage());
	}

	@Test
	void squadDTO_form_SquadView_constructorTest() {
		SquadView source = ViewDataUtils.createSquad();
		SquadDTO target = new SquadDTO(source);

		assertEquals(source.getId(), target.getId());
		assertEquals(source.getName(), target.getName());
		assertEquals(source.getCoverage(), target.getCoverage());
	}

}
