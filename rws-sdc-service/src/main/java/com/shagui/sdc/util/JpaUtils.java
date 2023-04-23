package com.shagui.sdc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

public class JpaUtils {
	private JpaUtils() {}
	
	public static String contains(String source) {
		List<String> words = StringUtils.hasText(source) ? Arrays.asList(source.split("\\s+")) : new ArrayList<>();
		String toFind = words.stream().filter(word -> word.length() > 1).collect(Collectors.joining("%"));
		
		return toFind.length() > 0 ? "%" + toFind + "%" :  null;
	}
}