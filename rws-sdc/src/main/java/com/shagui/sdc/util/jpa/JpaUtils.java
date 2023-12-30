package com.shagui.sdc.util.jpa;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

public class JpaUtils {
	private JpaUtils() {
	}

	public static String contains(String source) {
		if (!StringUtils.hasText(source))
			return null;

		String toFind = Arrays.stream(source.split("\\s+")).filter(word -> word.length() > 1)
				.collect(Collectors.joining("%"));

		return StringUtils.hasText(toFind) ? "%" + toFind + "%" : null;
	}

	public static String jpaStringMask(String value) {
		// Repalce all spaces with % to allow for partial matches
		return StringUtils.hasText(value) ? value.toLowerCase().replaceAll("\\s+", "%") : value;
	}
}