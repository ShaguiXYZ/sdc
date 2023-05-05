package com.shagui.sdc.util.validations;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.SETTER;
import org.junit.jupiter.api.Test;

class MetricControlTest {

	@Test
	void PojoValidationsTest() {
		assertPojoMethodsFor(MetricControl.class).testing(GETTER, SETTER, CONSTRUCTOR).areWellImplemented();
	}

}
