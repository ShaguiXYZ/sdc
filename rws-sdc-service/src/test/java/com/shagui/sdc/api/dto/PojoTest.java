package com.shagui.sdc.api.dto;

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

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.PageInfo;

import pl.pojo.tester.api.DefaultPackageFilter;
import pl.pojo.tester.api.PackageFilter;

@DisplayName("Tests for DTOs")
class PojoTest {

	@Test
	@DisplayName("Tests for DTOs to ensure POJO methods are well implemented")
	void testDTO() {
		assertPojoMethodsFor(AnalysisValuesDTO.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(ArchitectureDTO.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(ComponentDTO.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(ComponentTypeDTO.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(MetricAnalysisDTO.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(MetricDTO.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
		assertPojoMethodsFor(PageData.class).testing(CONSTRUCTOR, GETTER).areWellImplemented();	
		assertPojoMethodsFor(PageInfo.class).testing(GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();	
		assertPojoMethodsFor(SquadDTO.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();	
		assertPojoMethodsFor(DepartmentDTO.class).testing(GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();	
		assertPojoMethodsFor(HistoricalCoverageDTO.class).testing(GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		assertPojoMethodsFor(TimeCoverageDTO.class).testing(GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		assertPojoMethodsFor(UriDTO.class).testing(GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();	


	}
	
	@Test
	@DisplayName("Tests for sonar DTOs to ensure POJO methods are well implemented")
	void testSonarDTO() {
		
		
		PackageFilter sonarDTO = DefaultPackageFilter.forPackage("com.shagui.sdc.api.dto.sonar");
		
		assertPojoMethodsForAll(sonarDTO).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
		
	}
	
	@Test
	@DisplayName("Tests for git DTOs to ensure POJO methods are well implemented")
	void testGitDTO() {
		
		
		PackageFilter gitDTO = DefaultPackageFilter.forPackage("com.shagui.sdc.api.dto.git");
		
		assertPojoMethodsForAll(gitDTO).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, HASH_CODE, EQUALS).areWellImplemented();
		
	}

}