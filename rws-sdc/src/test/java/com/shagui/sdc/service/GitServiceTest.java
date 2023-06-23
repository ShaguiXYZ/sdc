package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shagui.sdc.api.client.GitClient;
import com.shagui.sdc.api.dto.git.ContentDTO;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.ComponentUrisModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.pk.ComponentUriPk;
import com.shagui.sdc.service.impl.GitXmlServiceImpl;
import com.shagui.sdc.test.utils.RwsTestUtils;
import com.shagui.sdc.util.UrlUtils;

class GitServiceTest {

	@InjectMocks
	GitXmlServiceImpl service;

	@Mock
	private GitClient gitClient;

	@Mock
	private ObjectMapper objectMapper;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);

		UrlUtils.setConfig(RwsTestUtils.urlUtilsConfig());

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
	void testAnalyzeRuntimeException() {
		ComponentModel component = new ComponentModel();

		List<MetricModel> metrics = new ArrayList<MetricModel>();
		MetricModel metricModel = new MetricModel();
		metricModel.setType(AnalysisType.GIT_XML);
		metrics.add(metricModel);

		List<ComponentUrisModel> uris = new ArrayList<>();
		ComponentUrisModel uriModel = new ComponentUrisModel();
		uriModel.setId(new ComponentUriPk(0, "uri_name"));
		uris.add(uriModel);

		List<ComponentPropertyModel> properties = new ArrayList<ComponentPropertyModel>();
		ComponentPropertyModel componentProperty = new ComponentPropertyModel();
		componentProperty.setName("xml_path");

		ComponentTypeArchitectureModel componentTypeArchitecture = new ComponentTypeArchitectureModel();
		componentTypeArchitecture.setMetrics(metrics);

		component.setComponentTypeArchitecture(componentTypeArchitecture);
		component.setUris(uris);
		component.setProperties(properties);

		when(gitClient.repoFile(any(URI.class)))
				.thenReturn(RwsTestUtils.response(400, RwsTestUtils.JSON_COMPONENT_DTO_TEST));

		assertThrows(RuntimeException.class, () -> service.analyze(component));
	}

	@Test
	void testAnalyzeEmptyMetricsNotEmpty() throws JsonParseException, JsonMappingException, IOException {
		ComponentModel component = new ComponentModel();

		List<MetricModel> metrics = new ArrayList<MetricModel>();
		MetricModel metricModel = new MetricModel();
		metricModel.setType(AnalysisType.GIT_XML);
		metrics.add(metricModel);

		List<ComponentUrisModel> uris = new ArrayList<>();
		ComponentUrisModel uriModel = new ComponentUrisModel();
		uriModel.setId(new ComponentUriPk(0, "uri_name"));
		uris.add(uriModel);

		List<ComponentPropertyModel> properties = new ArrayList<ComponentPropertyModel>();
		ComponentPropertyModel componentProperty = new ComponentPropertyModel();
		componentProperty.setName("xml_path");

		ComponentTypeArchitectureModel componentTypeArchitecture = new ComponentTypeArchitectureModel();
		componentTypeArchitecture.setMetrics(metrics);

		component.setComponentTypeArchitecture(componentTypeArchitecture);
		component.setUris(uris);
		component.setProperties(properties);

		ContentDTO contentDTO = new ContentDTO();
		contentDTO.setName("test");

		when(gitClient.repoFile(any(URI.class)))
				.thenReturn(RwsTestUtils.response(200, RwsTestUtils.JSON_COMPONENT_DTO_TEST));

		assertThrows(NullPointerException.class, () -> service.analyze(component));
	}

}
