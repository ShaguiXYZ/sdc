package com.shagui.sdc.util.validations;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.SETTER;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

import org.junit.jupiter.api.Test;

class MetricControlTest {

	@Test
	void PojoValidationsTest() {
		assertPojoMethodsFor(MetricControl.class).testing(GETTER, SETTER, TO_STRING).areWellImplemented();
	}

}
