package com.shagui.sdc.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;
import static pl.pojo.tester.api.assertion.Method.SETTER;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

import org.junit.jupiter.api.Test;

import com.shagui.sdc.model.pk.ComponentAnalysisPk;

class ModelTest {

	@Test
	void pojoModelTest() {
		assertPojoMethodsFor(ArchitectureModel.class).testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE)
				.areWellImplemented();
		assertPojoMethodsFor(CompanyModel.class).testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE)
				.areWellImplemented();
		assertPojoMethodsFor(ComponentAnalysisModel.class).testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE)
				.areWellImplemented();
		assertPojoMethodsFor(ComponentModel.class).testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE)
				.areWellImplemented();
		assertPojoMethodsFor(ComponentPropertyModel.class).testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE)
				.areWellImplemented();
		assertPojoMethodsFor(ComponentTypeArchitectureModel.class).testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE)
				.areWellImplemented();
		assertPojoMethodsFor(ComponentTypeModel.class).testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE)
				.areWellImplemented();
		assertPojoMethodsFor(DepartmentModel.class).testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE)
				.areWellImplemented();
		assertPojoMethodsFor(MetricModel.class).testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE)
				.areWellImplemented();
		assertPojoMethodsFor(MetricValuesModel.class).testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE)
				.areWellImplemented();
		assertPojoMethodsFor(SquadModel.class).testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE)
				.areWellImplemented();
		assertPojoMethodsFor(UriModel.class).testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE).areWellImplemented();
		assertPojoMethodsFor(RequestPropertiesModel.class).testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE)
				.areWellImplemented();
		assertPojoMethodsFor(ComponentAnalysisPk.class).testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE)
				.areWellImplemented();
		assertPojoMethodsFor(ComponentHistoricalCoverageModel.class)
				.testing(GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE).areWellImplemented();
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
	void componentTypeArchitectureModelTest() {

		ComponentTypeModel componentType = new ComponentTypeModel();
		ArchitectureModel architecture = new ArchitectureModel();
		ComponentTypeArchitectureModel model = new ComponentTypeArchitectureModel(componentType, architecture);
		assertNotNull(model);

	}

}
