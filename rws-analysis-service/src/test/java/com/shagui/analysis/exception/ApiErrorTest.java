package com.shagui.analysis.exception;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;
import static pl.pojo.tester.api.assertion.Method.SETTER;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

import org.junit.jupiter.api.Test;

import com.shagui.analysis.core.exception.ApiError;


class ApiErrorTest {
	
	@Test
	void Pojotest() {
		assertPojoMethodsFor(ApiError.class).testing(CONSTRUCTOR, GETTER, SETTER, TO_STRING, EQUALS, HASH_CODE).areWellImplemented();
	}

}
