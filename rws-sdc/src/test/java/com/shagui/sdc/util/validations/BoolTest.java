package com.shagui.sdc.util.validations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class BoolTest {

//	@Test
//	void pojoModelTest() {
//		final Object[] constructorParameters = { "true" };
//		Class<?>[] constructorParameterTypes = { String.class };
//		assertPojoMethodsFor(Bool.class).create(Bool.class, constructorParameters, constructorParameterTypes)
//				.testing(EQUALS, HASH_CODE).areWellImplemented();
//	}

	@Test
	void constructorTest() {
		Bool bool = new Bool("1");
		assertNotNull(bool);
	}

	@Test
	void compareToTest() {
		Bool bool = new Bool("1");
		String string = bool.toString();
		Integer integer = bool.compareTo(bool);
		assertNotNull(integer);
		assertNotNull(string);
	}
}
