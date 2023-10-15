package com.shagui.sdc.api.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

class TimeCoverageTest {
	@Test
	void testTimeCoverageConstructor() {
		Float coverage = 0.5f;
		Date analysisDate = new Date();
		TimeCoverage source = new TimeCoverage();
		source.setCoverage(coverage);
		source.setAnalysisDate(analysisDate);

		TimeCoverage timeCoverage = new TimeCoverage(source);

		assertEquals(coverage, timeCoverage.getCoverage());
		assertEquals(analysisDate, timeCoverage.getAnalysisDate());
	}

	@Test
	void testTimeCoverage() {
		TimeCoverage timeCoverage = new TimeCoverage();
		assertNull(timeCoverage.getCoverage());

		Float coverage = 0.5f;
		timeCoverage.setCoverage(coverage);
		assertEquals(coverage, timeCoverage.getCoverage());
	}
}
