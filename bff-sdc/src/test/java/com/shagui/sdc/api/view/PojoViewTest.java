package com.shagui.sdc.api.view;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;
import static pl.pojo.tester.api.assertion.Method.SETTER;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.PageInfo;

class PojoViewTest {

	@Test
	@DisplayName("Tests for Views to ensure POJO methods are well implemented")
	void testView() {
		assertPojoMethodsFor(AnalysisValuesView.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		assertPojoMethodsFor(ArchitectureView.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		assertPojoMethodsFor(ComponentView.class).testing(GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		assertPojoMethodsFor(ComponentTypeView.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		assertPojoMethodsFor(MetricAnalysisView.class).testing(GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		assertPojoMethodsFor(MetricView.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		assertPojoMethodsFor(PageData.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();	
		assertPojoMethodsFor(PageInfo.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();	
		assertPojoMethodsFor(SquadView.class).testing(GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();	
	}

}
