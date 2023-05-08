package com.shagui.sdc.util.validations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class NumericTest {

	@Test
	void test() {
		Numeric numeric = new Numeric("90");
		String result = numeric.toString();
		assertEquals("90.0", result);
	}

}
