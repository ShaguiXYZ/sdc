package com.shagui.sdc.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictioraryReplacement {

	private DictioraryReplacement() {
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
			Pattern p = Pattern.compile("(?<=\\$\\{)([\\w\\-]*)(?=\\})");
			Matcher m = p.matcher(source);

			String result = source;
			Set<String> keys = new HashSet<>();

			while (m.find()) {
				String key = m.group();

				if (keys.add(key) && dictionary.containsKey(key)) {
					result = result.replace("${" + key + "}", dictionary.get(key));
				}
			}

			return result;
		}
	}
}
