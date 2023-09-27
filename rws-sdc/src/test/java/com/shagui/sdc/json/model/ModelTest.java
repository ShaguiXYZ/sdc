package com.shagui.sdc.json.model;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.SETTER;

import org.junit.jupiter.api.Test;

class ModelTest {

	@Test
	void pojoModelTest() {
		assertPojoMethodsFor(ComponentParamsModel.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(ParamConfigModel.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(DataListModel.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(UriModel.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
		assertPojoMethodsFor(RequestPropertiesModel.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
	}
}
