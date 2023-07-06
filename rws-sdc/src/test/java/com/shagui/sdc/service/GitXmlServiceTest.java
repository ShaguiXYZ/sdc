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
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.StaticRepositoryConfig;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.ComponentUriModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.pk.ComponentUriPk;
import com.shagui.sdc.service.impl.GitXmlServiceImpl;
import com.shagui.sdc.test.utils.RwsTestUtils;
import com.shagui.sdc.util.UrlUtils;

class GitXmlServiceTest {

	@InjectMocks
	GitXmlServiceImpl service;

	@Mock
	private GitClient gitClient;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private StaticRepositoryConfig staticRepositoryConfig;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		StaticRepository.setConfig(staticRepositoryConfig);

		UrlUtils.setConfig(RwsTestUtils.urlUtilsConfig());
		when(staticRepositoryConfig.uris()).thenReturn(new ArrayList<>() {
			private static final long serialVersionUID = 1L;

			{
				UriModel uri = new UriModel();
				uri.setName("uri_name");
				uri.setType(AnalysisType.GIT);
				uri.setProperties(new ArrayList<>());
				add(uri);
			}
		});
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

		List<ComponentUriModel> uris = new ArrayList<>();
		ComponentUriModel uriModel = new ComponentUriModel();
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

		when(gitClient.repoFile(any(URI.class))).thenReturn(
				RwsTestUtils.response(400, RwsTestUtils.gitContentResponse(RwsTestUtils.XML_RESPONSE_TEST)));

		assertThrows(RuntimeException.class, () -> service.analyze(component));
	}

	@Test
	void testAnalyzeXmlMetricsNotEmpty() throws JsonParseException, JsonMappingException, IOException {

		List<MetricModel> metrics = new ArrayList<MetricModel>();
		MetricModel metricModel = new MetricModel();
		metricModel.setId(1);
		metricModel.setName("project/properties/version");
		metricModel.setType(AnalysisType.GIT_XML);
		metrics.add(metricModel);

		List<ComponentUriModel> uris = new ArrayList<>();
		ComponentUriModel uriModel = new ComponentUriModel();
		uriModel.setId(new ComponentUriPk(0, "uri_name"));
		uris.add(uriModel);

		List<ComponentPropertyModel> properties = new ArrayList<ComponentPropertyModel>();
		ComponentPropertyModel componentProperty = new ComponentPropertyModel();
		componentProperty.setName("xml_path");

		ComponentTypeArchitectureModel componentTypeArchitecture = new ComponentTypeArchitectureModel();
		componentTypeArchitecture.setId(1);
		componentTypeArchitecture.setMetrics(metrics);

		ComponentModel component = new ComponentModel();
		component.setId(1);
		component.setComponentTypeArchitecture(componentTypeArchitecture);
		component.setUris(uris);
		component.setProperties(properties);

		when(gitClient.repoFile(any(URI.class))).thenReturn(
				RwsTestUtils.response(200, RwsTestUtils.gitContentResponse(RwsTestUtils.XML_RESPONSE_TEST)));

		List<ComponentAnalysisModel> analysis = service.analyze(component);

		assertEquals(1, analysis.size());
		assertEquals("1", analysis.get(0).getValue());
	}

}
