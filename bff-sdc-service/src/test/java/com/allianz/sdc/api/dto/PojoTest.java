package com.allianz.sdc.api.dto;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;
import static pl.pojo.tester.api.assertion.Method.SETTER;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.allianz.sdc.core.dto.AuthenticationDTO;

class PojoTest {

	@Test
	@DisplayName("Tests for DTOs to ensure POJO methods are well implemented")
	void testDTO() {
		assertPojoMethodsFor(AnalysisValuesDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		assertPojoMethodsFor(ArchitectureDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		assertPojoMethodsFor(ComponentDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		assertPojoMethodsFor(ComponentTypeDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		assertPojoMethodsFor(MetricAnalysisDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		assertPojoMethodsFor(MetricDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		assertPojoMethodsFor(SquadDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();	
		assertPojoMethodsFor(AuthenticationDTO.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();	
	}

}
