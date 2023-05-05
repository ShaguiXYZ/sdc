package com.shagui.sdc.util.validations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.shagui.sdc.api.dto.AnalysisValuesDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.enums.MetricType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;

class MetricValidationsTest {
	
	@Test
	void validateEquals() {
		MetricDTO metric = new MetricDTO(1, "test", MetricType.GIT, MetricValueType.NUMERIC, MetricValidation.EQ);
		AnalysisValuesDTO analysisValues = new AnalysisValuesDTO(10, "10", "50", "75", "100");
		MetricAnalysisDTO analysis = new MetricAnalysisDTO(new Date(), metric, analysisValues, 0.5F);
		Float result = MetricValidations.validate(analysis);
		assertNotNull(result);
	}
	
	@Test
	void validateMayor() {
		MetricDTO metric = new MetricDTO(1, "test", MetricType.GIT, MetricValueType.NUMERIC, MetricValidation.GT);
		AnalysisValuesDTO analysisValues = new AnalysisValuesDTO(10, "10", "50", "75", "100");
		MetricAnalysisDTO analysis = new MetricAnalysisDTO(new Date(), metric, analysisValues, 0.5F);
		Float result = MetricValidations.validate(analysis);
		assertNotNull(result);
	}
	
	@Test
	void validateMinor() {
		MetricDTO metric = new MetricDTO(1, "test", MetricType.GIT, MetricValueType.NUMERIC, MetricValidation.LT);
		AnalysisValuesDTO analysisValues = new AnalysisValuesDTO(10, "10", "50", "75", "100");
		MetricAnalysisDTO analysis = new MetricAnalysisDTO(new Date(), metric, analysisValues, 0.5F);
		Float result = MetricValidations.validate(analysis);
		assertNotNull(result);
	}
	
	@Test
	void validateMayorOrEquals() {
		MetricDTO metric = new MetricDTO(1, "test", MetricType.GIT, MetricValueType.NUMERIC, MetricValidation.GTE);
		AnalysisValuesDTO analysisValues = new AnalysisValuesDTO(10, "10", "50", "75", "100");
		MetricAnalysisDTO analysis = new MetricAnalysisDTO(new Date(), metric, analysisValues, 0.5F);
		Float result = MetricValidations.validate(analysis);
		assertNotNull(result);
	}
	
	@Test
	void validateMinorOrEquals() {
		MetricDTO metric = new MetricDTO(1, "test", MetricType.GIT, MetricValueType.NUMERIC, MetricValidation.LTE);
		AnalysisValuesDTO analysisValues = new AnalysisValuesDTO(10, "10", "50", "75", "100");
		MetricAnalysisDTO analysis = new MetricAnalysisDTO(new Date(), metric, analysisValues, 0.5F);
		Float result = MetricValidations.validate(analysis);
		assertNotNull(result);
	}

}
