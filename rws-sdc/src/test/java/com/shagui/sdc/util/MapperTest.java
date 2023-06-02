package com.shagui.sdc.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.pk.ComponentAnalysisPk;
import com.shagui.sdc.test.utils.RwsTestUtils;

class MapperTest {
	
	@BeforeEach
	void init(){
	    MockitoAnnotations.openMocks(this);
		Mapper.setConfig(RwsTestUtils.mapperConfig());
	}
	
	@Test
	void parseComponentAnalysisModelTest() {
		ComponentAnalysisModel source = new ComponentAnalysisModel();
		
		MetricModel metric = new MetricModel();
		metric.setId(1);
		metric.setName("test");
		metric.setType(AnalysisType.GIT);
		metric.setValidation(MetricValidation.EQ);
		metric.setValueType(MetricValueType.NUMERIC);
		
		ComponentAnalysisPk id = new ComponentAnalysisPk();
		id.setAnalysisDate(new Date(0));
		
		source.setMetric(metric);
		source.setId(id);
		
		MetricAnalysisDTO result = Mapper.parse(source);
		assertNotNull(result);
	}

}
