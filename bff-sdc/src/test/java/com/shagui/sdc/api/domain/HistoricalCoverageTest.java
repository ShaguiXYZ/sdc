package com.shagui.sdc.api.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class HistoricalCoverageTest {
	@Test
	void constructor() {
		HistoricalCoverage<String> historicalCoverage = new HistoricalCoverage<>();
		assertNotNull(historicalCoverage);
	}

	@Test
	void dataGetterAndSetter() {
		HistoricalCoverage<String> historicalCoverage = new HistoricalCoverage<>();
		historicalCoverage.setData("test");
		assertEquals("test", historicalCoverage.getData());
	}

	@Test
	void historicalGetterAndSetter() {
		HistoricalCoverage<String> historicalCoverage = new HistoricalCoverage<>();
		PageData<TimeCoverage> pageData = new PageData<>();
		historicalCoverage.setHistorical(pageData);
		assertEquals(pageData, historicalCoverage.getHistorical());
	}
}
