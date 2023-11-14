package com.shagui.sdc.api.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.shagui.sdc.api.dto.AnalysisValuesDTO;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.test.utils.DtoDataUtils;

class PojoViewTest {

//	@Test
//	@DisplayName("Tests for Views to ensure POJO methods are well implemented")
//	void testView() {
//		assertPojoMethodsFor(AnalysisValuesView.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
//		assertPojoMethodsFor(ComponentView.class).testing(GETTER, SETTER).areWellImplemented();
//		assertPojoMethodsFor(MetricAnalysisView.class).testing(GETTER, SETTER).areWellImplemented();
//		assertPojoMethodsFor(MetricView.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
//		assertPojoMethodsFor(SquadView.class).testing(GETTER, SETTER).areWellImplemented();
//		assertPojoMethodsFor(DepartmentView.class).testing(GETTER, SETTER).areWellImplemented();
//
//		assertPojoMethodsFor(PageData.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
//		assertPojoMethodsFor(PageInfo.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
//	}

	@Test
	void analysisValuesView_form_AnalysisValuesDTO_constructorTest() {
		AnalysisValuesDTO source = DtoDataUtils.createAnalysisValues();
		AnalysisValuesView target = new AnalysisValuesView(source);

		assertEquals(source.getMetricValue(), target.getMetricValue());
		assertEquals(source.getExpectedValue(), target.getExpectedValue());
		assertEquals(source.getGoodValue(), target.getGoodValue());
		assertEquals(source.getPerfectValue(), target.getPerfectValue());
	}

	@Test
	void componentView_form_ComponentDTO_constructorTest() {
		ComponentDTO source = DtoDataUtils.createComponent();
		source.setSquad(DtoDataUtils.createSquad());
		ComponentView target = new ComponentView(source);

		assertEquals(source.getId(), target.getId());
		assertEquals(source.getName(), target.getName());
		assertEquals(source.getAnalysisDate(), target.getAnalysisDate());
	}

	@Test
	void metricView_form_MetricDTO_constructorTest() {
		MetricDTO source = DtoDataUtils.createMetric(null, null, null);
		MetricView target = new MetricView(source);

		assertEquals(source.getId(), target.getId());
		assertEquals(source.getName(), target.getName());
	}

	@Test
	void metricAnalysisView_form_MetricAnalysisDTO_constructorTest() {
		MetricAnalysisDTO source = DtoDataUtils.createMetricAnalysis();
		source.setMetric(DtoDataUtils.createMetric(null, null, null));
		source.setAnalysisValues(DtoDataUtils.createAnalysisValues());
		MetricAnalysisView target = new MetricAnalysisView(source);

		assertEquals(source.getAnalysisDate(), target.getAnalysisDate());
		assertEquals(source.getCoverage(), target.getCoverage());
	}

	@Test
	void departmentView_form_DepartmentDTO_constructorTest() {
		DepartmentDTO source = DtoDataUtils.createDepartment();
		DepartmentView target = new DepartmentView(source);

		assertEquals(source.getId(), target.getId());
		assertEquals(source.getName(), target.getName());
		assertEquals(source.getCoverage(), target.getCoverage());
	}

	@Test
	void squadView_form_SquadDTO_constructorTest() {
		SquadDTO source = DtoDataUtils.createSquad();
		SquadView target = new SquadView(source);

		assertEquals(source.getId(), target.getId());
		assertEquals(source.getName(), target.getName());
		assertEquals(source.getCoverage(), target.getCoverage());
	}

}
