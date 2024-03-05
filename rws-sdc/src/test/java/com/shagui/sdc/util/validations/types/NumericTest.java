package com.shagui.sdc.util.validations.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class NumericTest {

	// @Test
	// void pojoModelTest() {
	// final Object[] constructorParameters = { "1" };
	// Class<?>[] constructorParameterTypes = { String.class };
	// assertPojoMethodsFor(Numeric.class).create(Numeric.class,
	// constructorParameters, constructorParameterTypes)
	// .testing(EQUALS, HASH_CODE).areWellImplemented();
	// }

	@Test
	void constructorTest() {
		Numeric numeric = new Numeric("90");
		String result = numeric.toString();
		assertEquals("90.0", result);
	}

	@Test
	void compareToTest() {
		Numeric numeric = new Numeric("1");
		String string = numeric.toString();
		Integer integer = numeric.compareTo(numeric);
		assertNotNull(integer);
		assertNotNull(string);
	}

}
