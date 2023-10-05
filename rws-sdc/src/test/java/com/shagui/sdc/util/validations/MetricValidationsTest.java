package com.shagui.sdc.util.validations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.test.utils.RwsTestUtils;

class MetricValidationsTest {

	@Test
	void validateEquals() {
		ComponentAnalysisModel analysis = RwsTestUtils.componentAnalysisModelMock("10");
		analysis.getMetric().setValidation(MetricValidation.EQ);

		Float result = MetricValidations.validate(analysis);
		assertNotNull(result);
	}

	@Test
	void validateNotEquals() {
		ComponentAnalysisModel analysis = RwsTestUtils.componentAnalysisModelMock("10");
		analysis.getMetric().setValidation(MetricValidation.NEQ);

		Float result = MetricValidations.validate(analysis);
		assertNotNull(result);
	}

	@Test
	void validateMayor() {
		ComponentAnalysisModel analysis = RwsTestUtils.componentAnalysisModelMock("10");
		analysis.getMetric().setValidation(MetricValidation.GT);

		Float result = MetricValidations.validate(analysis);
		assertNotNull(result);
	}

	@Test
	void validateMinor() {
		ComponentAnalysisModel analysis = RwsTestUtils.componentAnalysisModelMock("10");
		analysis.getMetric().setValidation(MetricValidation.LT);

		Float result = MetricValidations.validate(analysis);
		assertNotNull(result);
	}

	@Test
	void validateMayorOrEquals() {
		ComponentAnalysisModel analysis = RwsTestUtils.componentAnalysisModelMock("10");
		analysis.getMetric().setValidation(MetricValidation.GTE);

		Float result = MetricValidations.validate(analysis);
		assertNotNull(result);
	}

	@Test
	void validateMinorOrEquals() {
		ComponentAnalysisModel analysis = RwsTestUtils.componentAnalysisModelMock("10");
		analysis.getMetric().setValidation(MetricValidation.LTE);

		Float result = MetricValidations.validate(analysis);
		assertNotNull(result);
	}
}
