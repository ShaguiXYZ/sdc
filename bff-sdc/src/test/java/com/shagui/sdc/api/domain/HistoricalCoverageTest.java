package com.shagui.sdc.api.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class HistoricalCoverageTest {
	@Test
	void testConstructor() {
		HistoricalCoverage<String> historicalCoverage = new HistoricalCoverage<>();
		assertNotNull(historicalCoverage);
	}

	@Test
	void testDataGetterAndSetter() {
		HistoricalCoverage<String> historicalCoverage = new HistoricalCoverage<>();
		historicalCoverage.setData("test");
		assertEquals("test", historicalCoverage.getData());
	}

	@Test
	void testHistoricalGetterAndSetter() {
		HistoricalCoverage<String> historicalCoverage = new HistoricalCoverage<>();
		PageData<TimeCoverage> pageData = new PageData<>();
		historicalCoverage.setHistorical(pageData);
		assertEquals(pageData, historicalCoverage.getHistorical());
	}
}
