package com.shagui.sdc.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class TokenReplacement {

	private TokenReplacement() {
	}

	public static Replacement getInstance(Map<String, String> dictionary) {
		return new Replacement(dictionary);
	}

	public static class Replacement {
		private Map<String, String> dictionary;

		private Replacement(Map<String, String> dictionary) {
			this.dictionary = dictionary;
		}

		public String replace(String source) {
			Pattern p = Pattern.compile("(?<=\\$\\{)(.*)(?=\\})");
			Matcher m = p.matcher(source);

			var wrapper = new Object() {
				String result = source;
			};
			
			if (m.find()) {
				IntStream.range(0, m.groupCount()).filter(index -> dictionary.containsKey(m.group(index)))
						.forEach(index -> wrapper.result = wrapper.result.replace("${" + m.group(index) + "}",
								dictionary.get(m.group(index))));
			}

			return wrapper.result;
		}
	}
}
