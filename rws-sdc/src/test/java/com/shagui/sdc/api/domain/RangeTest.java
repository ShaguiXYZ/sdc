package com.shagui.sdc.api.domain;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.SETTER;

import org.junit.jupiter.api.Test;

class RangeTest {

	@Test
	void pojoTest() {
		assertPojoMethodsFor(Range.class).testing(CONSTRUCTOR, GETTER, SETTER).areWellImplemented();
	}

}
