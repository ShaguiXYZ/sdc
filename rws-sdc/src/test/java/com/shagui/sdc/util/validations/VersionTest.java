package com.shagui.sdc.util.validations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VersionTest {

	@Test
	void constructorTest() {
		
		Version version = new Version("1");
		assertNotNull(version);
		
	}
	
	@Test
	void compareToTest() {
		Version version = new Version("1");
		String string = version.toString();
		int integer = version.compareTo(version);
		assertNotNull(integer);
		assertNotNull(string);
	}

}
