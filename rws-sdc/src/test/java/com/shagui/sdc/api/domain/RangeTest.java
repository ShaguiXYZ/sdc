package com.shagui.sdc.api.domain;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;
import static pl.pojo.tester.api.assertion.Method.SETTER;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

import org.junit.jupiter.api.Test;

class RangeTest {

	@Test
	void pojoTest() {
		assertPojoMethodsFor(Range.class).testing(CONSTRUCTOR, GETTER, SETTER, EQUALS, HASH_CODE, TO_STRING)
				.areWellImplemented();

	}

}
