package com.shagui.sdc.util.jpa;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

/**
 * Utility class for JPA-related operations.
 */
public class JpaUtils {
	private JpaUtils() {
		// Prevent instantiation
	}

	/**
	 * Creates a SQL "contains" pattern from the given source string.
	 * 
	 * @param source The input string.
	 * @return A SQL "contains" pattern or {@code null} if the input is empty.
	 */
	public static String contains(String source) {
		if (!StringUtils.hasText(source))
			return null;

		String toFind = Arrays.stream(source.split("\\s+")).filter(word -> word.length() > 1)
				.collect(Collectors.joining("%"));

		return StringUtils.hasText(toFind) ? "%" + toFind + "%" : null;
	}
}