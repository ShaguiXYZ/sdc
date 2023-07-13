package com.shagui.sdc.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.shagui.sdc.core.exception.SdcCustomException;

class DictioraryReplacementTest {

	@Test
	void repalcementTest() {
		Map<String, String> dictionary = new HashMap<>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("key1", "value1");
			}
		};

		String value = DictioraryReplacement.getInstance(dictionary).replace("#{key1}");
		assertNotNull(value);
		assertEquals(dictionary.get("key1"), value);
	}

	@Test
	void defaultRepalcementTest() {
		Map<String, String> dictionary = new HashMap<>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("key1", "value1");
			}
		};

		String value = DictioraryReplacement.getInstance(dictionary).replace("-#{key}-", "default");
		assertNotNull(value);
		assertEquals("-default-", value);
	}
	
	@Test
	void strictReplacementTest() {
		Map<String, String> dictionary = new HashMap<>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("key1", "value1");
			}
		};

		SdcCustomException ex = null;
		try {
			DictioraryReplacement.getInstance(dictionary, true).replace("-#{key}-");
		} catch (SdcCustomException e) {
			ex = e;
		}

		assertNotNull(ex);
	}

}
