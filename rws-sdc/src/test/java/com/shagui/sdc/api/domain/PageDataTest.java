package com.shagui.sdc.api.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PageDataTest {

	private PageData<String> pageData;

	@BeforeEach
	void init() {
		List<String> page = new ArrayList<>();
		page.add("item1");
		page.add("item2");
		page.add("item3");
		pageData = new PageData<>(page);
	}

	@Test
	void constructorTest() {
		assertNotNull(pageData);
		assertEquals(3, pageData.getPage().size());
		assertEquals(0, pageData.getPaging().getPageIndex());
		assertEquals(3, pageData.getPaging().getPageSize());
		assertEquals(3, pageData.getPaging().getElements());
		assertEquals(1, pageData.getPaging().getPages());
	}

	@Test
	void gettersTest() {
		assertNotNull(pageData.getPage());
		assertNotNull(pageData.getPaging());
		assertEquals(3, pageData.getPage().size());
		assertEquals(0, pageData.getPaging().getPageIndex());
		assertEquals(3, pageData.getPaging().getPageSize());
		assertEquals(3, pageData.getPaging().getElements());
		assertEquals(1, pageData.getPaging().getPages());
	}
}