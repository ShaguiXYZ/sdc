package com.shagui.sdc.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

class IOUtilsTest {

	@Test
	void transformTest() throws IOException {
		String toTransform = "test transform";
		InputStream stream = IOUtils.toInputStream(toTransform);
		String string = IOUtils.toString(stream);
		
		assertEquals(toTransform, string);
	}

	@Test
	void transformJava8Test() throws IOException {
		String toTransform = "test transform";
		InputStream stream = IOUtils.toInputStream(toTransform);
		String string = IOUtils.toStringJava8(stream);
		
		assertEquals(toTransform, string);
	}

}
