package com.shagui.sdc.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.SETTER;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.shagui.sdc.model.pk.ComponentAnalysisPk;
import com.shagui.sdc.model.pk.ComponentHistoricalCoveragePk;

class ModelTest {

	@Test
	void pojoModelTest() {
		assertPojoMethodsFor(CompanyModel.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(ComponentAnalysisModel.class).testing(GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(ComponentModel.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(ComponentPropertyModel.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(ComponentTypeArchitectureModel.class).testing(GETTER, SETTER, CONSTRUCTOR)
				.areWellImplemented();
		assertPojoMethodsFor(DepartmentModel.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(MetricModel.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(MetricValuesModel.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(SquadModel.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(ComponentAnalysisPk.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(ComponentHistoricalCoverageModel.class).testing(GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(ComponentHistoricalCoveragePk.class).testing(GETTER, SETTER, CONSTRUCTOR)
				.areWellImplemented();
	}

	@Test
	void componentAnalysisModelTest() {
		ComponentModel component = new ComponentModel();
		component.setId(1);

		MetricModel metric = new MetricModel();
		metric.setId(1);

		String value = "test";
		ComponentAnalysisModel model = new ComponentAnalysisModel(component, metric, value);

		assertNotNull(model);
	}

	@Test
	void componentHistoricalCoverageModelTest() {
		ComponentModel componentModel = new ComponentModel();
		componentModel.setId(1);
		ComponentHistoricalCoverageModel model = new ComponentHistoricalCoverageModel(componentModel, new Date(),
				90.1f);
		assertNotNull(model);
	}

}
