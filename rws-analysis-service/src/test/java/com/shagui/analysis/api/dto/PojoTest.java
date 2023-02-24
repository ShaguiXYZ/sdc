package com.shagui.analysis.api.dto;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;
import static pl.pojo.tester.api.assertion.Method.SETTER;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shagui.analysis.api.dto.sonar.ComponentSonarDTO;
import com.shagui.analysis.api.dto.sonar.ComponentsSonarDTO;
import com.shagui.analysis.api.dto.sonar.MeasureSonarDTO;
import com.shagui.analysis.api.dto.sonar.MeasuresSonarDTO;

import pl.pojo.tester.api.PackageFilter;
import pl.pojo.tester.api.DefaultPackageFilter;

@DisplayName("Tests for DTOs")
class PojoTest {

	@Test
	@DisplayName("Tests for DTOs to ensure POJO methods are well implemented")
	void testDTO() {
		assertPojoMethodsFor(AnalysisValuesDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE).areWellImplemented();
		assertPojoMethodsFor(ArchitectureDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE).areWellImplemented();
		assertPojoMethodsFor(ComponentDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE).areWellImplemented();
		assertPojoMethodsFor(ComponentTypeDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE).areWellImplemented();
		assertPojoMethodsFor(MetricDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE).areWellImplemented();
		assertPojoMethodsFor(MetricAnalysisDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING).areWellImplemented();
		assertPojoMethodsFor(SquadDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE).areWellImplemented();
		assertPojoMethodsFor(PagingDTO.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE).areWellImplemented();		
	}
	
	@Test
	@DisplayName("Tests for sonar DTOs to ensure POJO methods are well implemented")
	void testSonarDTO() {
		
		
		PackageFilter sonarDTO = DefaultPackageFilter.forPackage("com.shagui.analysis.api.dto.sonar");
		
		assertPojoMethodsForAll(sonarDTO).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
		
	}

}