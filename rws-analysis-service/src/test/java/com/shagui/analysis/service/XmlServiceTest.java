package com.shagui.analysis.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.shagui.analysis.enums.MetricType;
import com.shagui.analysis.enums.UriType;
import com.shagui.analysis.model.ComponentAnalysisModel;
import com.shagui.analysis.model.ComponentModel;
import com.shagui.analysis.model.ComponentPropertyModel;
import com.shagui.analysis.model.ComponentTypeArchitectureModel;
import com.shagui.analysis.model.MetricModel;
import com.shagui.analysis.model.UriModel;
import com.shagui.analysis.service.impl.AnalysisServiceImpl;
import com.shagui.analysis.service.impl.XmlServiceImpl;

class XmlServiceTest {
	
	@InjectMocks
	XmlServiceImpl service;
	
	@BeforeEach
	void init(){
	    MockitoAnnotations.openMocks(this);
	}

	@Test
	void test() {
		ComponentModel component = new ComponentModel();
		
		List<MetricModel> metrics = new ArrayList<MetricModel>();
		MetricModel metricModel1 = new MetricModel();
		MetricModel metricModel2 = new MetricModel();
		metricModel1.setId(1);
		metricModel1.setType(MetricType.XML);
		metricModel2.setId(2);
		metricModel2.setType(MetricType.XML);
		metrics.add(metricModel1);
		metrics.add(metricModel2);
		
		ComponentTypeArchitectureModel componentTypeArchitecture = new ComponentTypeArchitectureModel();
		componentTypeArchitecture.setMetrics(metrics);
		
		component.setComponentTypeArchitecture(componentTypeArchitecture );
		
		List<UriModel> uris = new ArrayList<UriModel>();
		UriModel uriModel = new UriModel();
		uriModel.setId(1);
		uriModel.setUri("test");
		uriModel.setType(UriType.FILE);
		component.setUris(uris);
		
		List<ComponentAnalysisModel> analize = service.analyze(component);
	}

}
