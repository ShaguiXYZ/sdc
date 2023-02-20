package com.shagui.analysis.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.shagui.analysis.api.dto.AnalysisValuesDTO;
import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.api.dto.MetricDTO;
import com.shagui.analysis.enums.MetricState;
import com.shagui.analysis.enums.MetricValueType;

class MetricValidationsTest {
	
	@BeforeEach
	void init(){
	    MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void validateStateTest() {
		MetricDTO metric = new MetricDTO();
		metric.setValueType(MetricValueType.NUMERIC);
		AnalysisValuesDTO analysisvalues = new AnalysisValuesDTO("test", "test", "test", "test");
		MetricState metricState = null;
		MetricAnalysisDTO source = new MetricAnalysisDTO(new Date(), metric , analysisvalues , metricState);
		MetricValidations.validateState(source);
	}

	@Test
	void validateStateTestNull() {
		MetricDTO metric = new MetricDTO();
		AnalysisValuesDTO analysisvalues = new AnalysisValuesDTO("test", "test", "test", "test");
		MetricState metricState = null;
		MetricAnalysisDTO source = new MetricAnalysisDTO(new Date(), metric , analysisvalues , metricState);
		MetricState state = MetricValidations.validateState(source);
		assertNull(state);
	}

}
