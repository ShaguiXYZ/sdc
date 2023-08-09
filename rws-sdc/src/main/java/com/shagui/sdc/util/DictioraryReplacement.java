package com.shagui.sdc.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.shagui.sdc.core.exception.SdcCustomException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DictioraryReplacement {
	private DictioraryReplacement() {
	}

	public static Replacement getInstance(Map<String, String> dictionary) {
		return getInstance(dictionary, false);
	}

	public static Replacement getInstance(Map<String, String> dictionary, boolean strict) {
		return new Replacement(dictionary, strict);
	}

	public static class Replacement {
		private static final String PRE_EXP = "\\#\\{";
		private static final String POST_EXP = "\\}";

		private boolean strict;
		private Map<String, String> dictionary;

		private Replacement(Map<String, String> dictionary, boolean strict) {
			this.strict = strict;
			this.dictionary = dictionary;
		}

		public String replace(String source) {
			return replace(source, null);
		}

		public String replace(String source, String defaultValue) {
			Pattern p = Pattern.compile("(?<=" + PRE_EXP + ")([$]?)([\\w\\-]*)(?=" + POST_EXP + ")");
			Matcher m = p.matcher(source);

			String result = source;
			Set<String> keys = new HashSet<>();

			while (m.find()) {
				String key = m.group();

				if (keys.add(key)) {
					if (dictionary.containsKey(key)) {
						result = result.replaceAll(PRE_EXP + key.replace("$", "\\$") + POST_EXP, dictionary.get(key));
					} else {
						if (strict) {
							throw new SdcCustomException(String.format("[STRICT] Not key %s found in repository", key));
						}

						log.debug(String.format("Not key %s found in repository", key));

						if (defaultValue != null) {
							result = result.replaceAll(PRE_EXP + key + POST_EXP, defaultValue);
						}
					}
				}
			}

			return result;
		}
	}
}
