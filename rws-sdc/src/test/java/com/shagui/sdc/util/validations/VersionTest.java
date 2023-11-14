package com.shagui.sdc.util.validations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class VersionTest {

//	@Test
//	void pojoModelTest() {
//		final Object[] constructorParameters = { "1.1" };
//		Class<?>[] constructorParameterTypes = { String.class };
//		assertPojoMethodsFor(Version.class).create(Version.class, constructorParameters, constructorParameterTypes)
//				.testing(EQUALS, HASH_CODE).areWellImplemented();
//	}

	@Test
	void constructorTest() {
		Version version = new Version("1");
		assertNotNull(version);
	}

	@Test
	void compareToTest() {
		Version version = new Version("1");
		String string = version.toString();
		Integer integer = version.compareTo(version);
		assertNotNull(integer);
		assertNotNull(string);
	}

}
