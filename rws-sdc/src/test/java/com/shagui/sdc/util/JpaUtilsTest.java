package com.shagui.sdc.util;

import org.junit.jupiter.api.Test;

class JpaUtilsTest {
	
	@Test
	void containsTest() {
		JpaUtils.contains("a");
	}

	@Test
	void containsHasTextTest() {
		JpaUtils.contains("test");
	}
	
	@Test
	void containsNotHasTextTest() {
		JpaUtils.contains(null);
	}

}
