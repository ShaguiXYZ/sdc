package com.shagui.analysis.config;

import static org.junit.jupiter.api.Assertions.*;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;
import static pl.pojo.tester.api.assertion.Method.SETTER;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.cors.CorsConfiguration;

import com.shagui.analysis.config.SecurityProperties.Cors;

@DisplayName("Test for Security properties")
class SecurityPropertiesTest {
	
	@InjectMocks
	SecurityProperties properties;
	
	@Mock
	Cors cors;

	@BeforeEach
	void init(){
	    MockitoAnnotations.openMocks(this);
	}
	
	@Test
	@DisplayName("Test for SecurityProperties class to ensure POJO methods are well implemented")
	void pojoTest() {
		assertPojoMethodsFor(SecurityProperties.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE).areWellImplemented();
	}
	
	@Test
	@DisplayName("Test for the getter method getCorsConfiguration")
	void getCorsConfigurationTest() {
		CorsConfiguration corsConfiguration = properties.getCorsConfiguration();
		assertNotNull(corsConfiguration);
	}

}
