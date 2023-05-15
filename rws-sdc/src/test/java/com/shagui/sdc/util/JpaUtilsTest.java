package com.shagui.sdc.util;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class JpaUtilsTest {

	@Test
	void containsHasTextTest() {
		String result = JpaUtils.contains("data1 data2");
		assertEquals("%data1%data2%", result);
	}
	
	@Test
	void containsNotHasTextTest() {
		String result = JpaUtils.contains(null);
		assertNull(result);
	}

}
