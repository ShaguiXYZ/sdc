package com.shagui.sdc.core.configuration;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.GETTER;

import org.junit.jupiter.api.Test;

class AppConfigTest {

	@Test
	void getterTest() {
		assertPojoMethodsFor(AppConfig.class).testing(GETTER).areWellImplemented();

	}

}
