package com.shagui.sdc.util.validations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

import org.junit.jupiter.api.Test;

class NumericTest {

	@Test
	void pojoModelTest() {
		final Object[] constructorParameters = { "1" };
		Class<?>[] constructorParameterTypes = { String.class };
		assertPojoMethodsFor(Numeric.class).create(Numeric.class, constructorParameters, constructorParameterTypes)
				.testing(EQUALS, HASH_CODE).areWellImplemented();
	}

	@Test
	void constructorTest() {
		Numeric numeric = new Numeric("90");
		String result = numeric.toString();
		assertEquals("90.0", result);
	}

}
