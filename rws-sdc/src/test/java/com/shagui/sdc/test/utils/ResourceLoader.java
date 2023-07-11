package com.shagui.sdc.test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ResourceLoader {
	protected String loadFromPackageAsString(String fileName) throws IOException {
		return new String(getInputStream(fileName).readAllBytes(), StandardCharsets.UTF_8);
	}

	protected InputStream getInputStream(String fileName) {
		String resourceName = this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/" + fileName;
		return this.getClass().getClassLoader().getResourceAsStream(resourceName);
	}
}
