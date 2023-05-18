package com.shagui.sdc.api.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.SETTER;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

class RequestPageInfoTest {

	@Test
	void pojoTest() {
		assertPojoMethodsFor(RequestPageInfo.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
	}

	@Test
	void constructorTest() {
		RequestPageInfo info = new RequestPageInfo(1);
		assertNotNull(info);
	}

	@Test
	void constructorNullTest() {
		RequestPageInfo info = new RequestPageInfo(null, null);
		assertNotNull(info);
	}

	@Test
	void getPageableTest() {
		RequestPageInfo info = new RequestPageInfo(1, 1);
		Pageable result = info.getPageable();
		assertNotNull(result);
	}

}
