package com.shagui.sdc.util.collector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.shagui.sdc.api.domain.PageData;

class PageableCollectorTest {

	private List<String> list1 = new ArrayList<>();
	private List<String> list2 = new ArrayList<>();

	@BeforeEach
	void init() {
		list1.add("elem 1");
		list2.add("elem 2");
	}

	@Test
	void listToPageData() {
		PageData<String> page = list1.stream().collect(SdcCollectors.toPageable());
		assertNotNull(page);
		assertEquals(1, page.getPage().size());
	}

	@Test
	void combineListsToPageData() {
		PageData<String> page = list1.parallelStream().collect(SdcCollectors.toPageable());
		assertNotNull(page);
		assertEquals(1, page.getPage().size());
	}

}
