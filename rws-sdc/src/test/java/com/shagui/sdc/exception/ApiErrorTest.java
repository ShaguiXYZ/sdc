package com.shagui.sdc.exception;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shagui.sdc.core.exception.ApiError;

@DisplayName("Test for ApiError class")
class ApiErrorTest {
	
//	@Test
//	@DisplayName("Test for ApiError class to ensure POJO methods are well implemented")
//	void Pojotest() {
//		assertPojoMethodsFor(ApiError.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE).areWellImplemented();
//	}
	
	@Test
	void addErrorTest() {
		ApiError error = new ApiError();
		error.addError("error");
		assertNotNull(error);
	}

}
