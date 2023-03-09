package com.shagui.analysis.util;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.shagui.analysis.api.dto.AnalysisValuesDTO;
import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.api.dto.MetricDTO;
import com.shagui.analysis.enums.MetricValueType;
import com.shagui.analysis.util.validations.MetricValidations;

class MetricValidationsTest {
	
	@BeforeEach
	void init(){
	    MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void validateStateTest() {
		MetricDTO metric = new MetricDTO();
		metric.setValueType(MetricValueType.NUMERIC);
		AnalysisValuesDTO analysisvalues = new AnalysisValuesDTO(50, "test", "test", "test", "test");
		MetricAnalysisDTO source = new MetricAnalysisDTO(new Date(), metric , analysisvalues , 50f);
		MetricValidations.validate(source);
	}

	@Test
	void validateStateTestNull() {
		MetricDTO metric = new MetricDTO();
		AnalysisValuesDTO analysisvalues = new AnalysisValuesDTO(50, "test", "test", "test", "test");
		MetricAnalysisDTO source = new MetricAnalysisDTO(new Date(), metric , analysisvalues , 50f);
		Float state = MetricValidations.validate(source);
		assertNull(state);
	}

}
