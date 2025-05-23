package com.shagui.sdc.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.shagui.sdc.core.exception.SdcCustomException;

/**
 * The {@code DictioraryReplacement} class provides utility methods and nested
 * classes for performing dictionary-based string replacements. It allows users
 * to replace
 * placeholders in strings with corresponding values from a dictionary.
 *
 * <p>
 * The main functionality is provided by the nested {@code Replacement} class,
 * which supports both strict and non-strict replacement modes. Additionally,
 * the
 * {@code DictionaryPattern} class is used internally to generate regex patterns
 * for matching placeholders in strings.
 */
public class DictioraryReplacement {
	private DictioraryReplacement() {
		// Prevent instantiation
	}

	/**
	 * Creates a new {@code Replacement} instance with the given dictionary.
	 *
	 * @param dictionary the dictionary containing key-value pairs for replacements
	 * @return a {@code Replacement} instance
	 */
	public static Replacement getInstance(Map<String, String> dictionary) {
		return getInstance(dictionary, false);
	}

	/**
	 * Creates a new {@code Replacement} instance with the given dictionary and
	 * strict mode.
	 *
	 * @param dictionary the dictionary containing key-value pairs for replacements
	 * @param strict     whether to enable strict mode (throws an exception if a key
	 *                   is not found)
	 * @return a {@code Replacement} instance
	 */
	public static Replacement getInstance(Map<String, String> dictionary, boolean strict) {
		return new Replacement(dictionary, strict);
	}

	/**
	 * Extracts a function name from a string using regex.
	 *
	 * @param value the input string to search within
	 * @return an Optional containing the matched substring if found, otherwise an
	 *         empty Optional
	 */
	public static Optional<String> fn(String value) {
		/**
		 * @howto: (?<=\\#)([\\w]*)(?=\\{):
		 *         This regular expression matches a sequence of word characters
		 *         ([\\w]*) that is preceded by a '#' character (using a positive
		 *         lookbehind: (?<=\\#)) and followed by a '{' character (using a
		 *         positive lookahead: (?=\\{)).
		 * 
		 *         It is used to extract function names or keys that appear in the
		 *         format #functionName{.
		 */
		Pattern p = Pattern.compile("(?<=\\#)([\\w]*)(?=\\{)");
		Matcher m = p.matcher(value);

		return m.find() ? Optional.of(m.group().trim()) : Optional.empty();
	}

	public static Optional<String> value(String key) {
		return value(null, key);
	}

	/**
	 * Extracts a value from a string based on a key and optional characters.
	 *
	 * @param fn    the function name to include in the pattern (optional)
	 * @param key   the key to search for
	 * @param chars additional acceptable characters for the pattern
	 * @return an {@code Optional} containing the matched value, or an empty
	 *         {@code Optional} if no match is found
	 */
	public static Optional<String> value(String fn, String key, Character... chars) {
		Pattern p = Pattern.compile(DictionaryPattern.pattern(fn, chars));
		Matcher m = p.matcher(key);

		return m.find() ? Optional.of(m.group()) : Optional.empty();
	}

	/**
	 * The Replacement class provides functionality to replace occurrences of
	 * dictionary keys
	 * in a given source string with their corresponding values from a dictionary.
	 * It supports strict mode, which throws an exception if a key is not found in
	 * the dictionary.
	 */
	public static class Replacement {
		private static final UnaryOperator<String> PRE_EXP = fn -> "\\#" + Optional.ofNullable(fn).orElse("") + "\\{";
		private static final String POST_EXP = "\\}";

		private boolean strict;
		private Map<String, String> dictionary;

		private Replacement(Map<String, String> dictionary, boolean strict) {
			this.strict = strict;
			this.dictionary = dictionary;
		}

		/**
		 * Replaces occurrences in the given source string based on a dictionary.
		 *
		 * @param source the source string to perform replacements on
		 * @return the modified string with replacements applied
		 */
		public String replace(String source) {
			return replace(source, null);
		}

		/**
		 * Replaces occurrences of dictionary keys in the source string with their
		 * corresponding values.
		 * If the source string is null or empty, it uses an empty string for
		 * replacement.
		 *
		 * @param source       the source string in which to replace dictionary keys
		 * @param defaultValue the default value to use for replacement if a dictionary
		 *                     key is not found
		 * @return the resulting string after replacing dictionary keys with their
		 *         values
		 */
		public String replace(String source, String defaultValue) {
			String sourceToReplace = Optional.ofNullable(source).orElse("");
			String result = sourceToReplace;
			Pattern p = Pattern.compile(DictionaryPattern.pattern('-'));
			Matcher m = p.matcher(sourceToReplace);
			Set<String> keys = new HashSet<>();

			result = m.results()
					.map(MatchResult::group)
					.filter(keys::add)
					.reduce(result, (acc, key) -> replaceByDictionaryValue(key, acc, defaultValue));

			return result;
		}

		private String replaceByDictionaryValue(String key, String source, String defaultValue) {
			String result = source;

			if (dictionary.containsKey(key)) {
				result = source.replaceAll(PRE_EXP.apply(null) + key.replace("$", "\\$") + POST_EXP,
						dictionary.get(key));
			} else {
				if (strict) {
					throw new SdcCustomException("[STRICT] Not key '%s' found in repository".formatted(key));
				}

				if (defaultValue != null) {
					result = source.replaceAll(PRE_EXP.apply(null) + key + POST_EXP, defaultValue);
				}
			}

			return result;
		}
	}

	/**
	 * Utility class for generating regex patterns for dictionary replacements.
	 */
	private static final class DictionaryPattern {

		/**
		 * Generates a regex pattern for dictionary replacements with acceptable
		 * characters.
		 *
		 * @param acceptableChars the characters that are acceptable in the pattern
		 * @return the generated regex pattern as a String
		 */
		public static String pattern(Character... acceptableChars) {
			return pattern(null, acceptableChars);
		}

		/**
		 * Generates a regex pattern for dictionary replacements with a specified
		 * function name and acceptable characters.
		 *
		 * @param fn    the function name to be included in the pattern
		 * @param chars the characters that are acceptable in the pattern
		 * @return the generated regex pattern as a String
		 */
		public static String pattern(String fn, Character... chars) {
			return "(?<=" + Replacement.PRE_EXP.apply(fn) + ")([$]?)([\\w" + chars(chars) + "]*)(?="
					+ Replacement.POST_EXP + ")";
		}

		/**
		 * Converts an array of characters into a string suitable for inclusion in a
		 * regex pattern.
		 *
		 * @param chars the characters to be converted
		 * @return the characters as a string with each character escaped for regex
		 */
		private static String chars(Character... chars) {
			return Arrays.stream(chars).map(character -> "\\" + character.toString()).collect(Collectors.joining());
		}
	}
}
