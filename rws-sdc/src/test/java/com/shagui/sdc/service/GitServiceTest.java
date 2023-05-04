package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.enums.MetricType;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.UriModel;
import com.shagui.sdc.service.impl.GitServiceImpl;

class GitServiceTest {

	@InjectMocks
	GitServiceImpl service;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAnalyzeEmptyMetricsEmpty() {
		ComponentModel component = new ComponentModel();

		List<MetricModel> metrics = new ArrayList<MetricModel>();

		ComponentTypeArchitectureModel componentTypeArchitecture = new ComponentTypeArchitectureModel();
		componentTypeArchitecture.setMetrics(metrics);

		component.setComponentTypeArchitecture(componentTypeArchitecture);

		List<ComponentAnalysisModel> analize = service.analyze(component);
		assertEquals(new ArrayList<>(), analize);
	}

	@Test
	void testAnalyzeEmptyMetricsNotEmpty() {
		ComponentModel component = new ComponentModel();

		List<MetricModel> metrics = new ArrayList<MetricModel>();
		MetricModel metricModel = new MetricModel();
		metricModel.setType(MetricType.GIT);
		metrics.add(metricModel);

		List<UriModel> uris = new ArrayList<UriModel>();
		UriModel uriModel = new UriModel();
		uriModel.setType(UriType.GIT);

		List<ComponentPropertyModel> properties = new ArrayList<ComponentPropertyModel>();
		ComponentPropertyModel componentProperty = new ComponentPropertyModel();
		componentProperty.setName("xml_path");

		ComponentTypeArchitectureModel componentTypeArchitecture = new ComponentTypeArchitectureModel();
		componentTypeArchitecture.setMetrics(metrics);

		component.setComponentTypeArchitecture(componentTypeArchitecture);
		component.setUris(uris);
		component.setProperties(properties);

		List<ComponentAnalysisModel> analize = service.analyze(component);
		assertEquals(new ArrayList<>(), analize);
	}

}
