package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.StaticRepositoryConfig;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.ComponentUriModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.pk.ComponentUriPk;
import com.shagui.sdc.repository.ComponentTypeArchitectureMetricPropertiesRepository;
import com.shagui.sdc.service.impl.GitXmlServiceImpl;
import com.shagui.sdc.test.utils.ReflectUtils;
import com.shagui.sdc.test.utils.RwsTestUtils;
import com.shagui.sdc.util.UrlUtils;
import com.shagui.sdc.util.git.GitUtils;
import com.shagui.sdc.util.git.GitUtilsConfig;

class GitXmlServiceTest {

	@InjectMocks
	GitXmlServiceImpl service;

	@Mock
	private SseService sseService;

	@Mock
	private GitClient gitClient;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private ComponentTypeArchitectureMetricPropertiesRepository componentTypeArchitectureMetricPropertiesRep;

	@Mock
	private StaticRepositoryConfig staticRepositoryConfig;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);

		ReflectUtils.invoke(GitUtils.class, "setConfig", new GitUtilsConfig(gitClient));
		ReflectUtils.invoke(StaticRepository.class, "setConfig", staticRepositoryConfig);
		ReflectUtils.invoke(UrlUtils.class, "setConfig", RwsTestUtils.urlUtilsConfig());

		when(staticRepositoryConfig.uris().values()).thenReturn(new ArrayList<>() {
			private static final long serialVersionUID = 1L;

			{
				add(RwsTestUtils.uriModelMock(UriType.GIT));
			}
		});

		GitDocumentService
				.setConfig(new GitDocumentServiceConfig(sseService, componentTypeArchitectureMetricPropertiesRep));
	}

	@Test
	void analyzeEmptyMetricsEmpty() {
		List<MetricModel> metrics = new ArrayList<MetricModel>();

		List<ComponentUriModel> uris = new ArrayList<>();
		uris.add(RwsTestUtils.componentUriModelMock());

		List<ComponentPropertyModel> properties = new ArrayList<ComponentPropertyModel>();
		properties.add(RwsTestUtils.componentProperty("xml_path"));

		ComponentTypeArchitectureModel componentTypeArchitecture = new ComponentTypeArchitectureModel();
		componentTypeArchitecture.setMetrics(metrics);

		ComponentModel component = new ComponentModel();
		component.setComponentTypeArchitecture(componentTypeArchitecture);
		component.setUris(uris);
		component.setProperties(properties);

		List<ComponentAnalysisModel> analize = service.analyze("workflowId", component);
		assertEquals(new ArrayList<>(), analize);
	}

	@Test
	void analyzeXmlMetricsNotEmpty() throws JsonParseException, JsonMappingException, IOException {
		List<MetricModel> metrics = new ArrayList<MetricModel>();
		MetricModel metricModel = new MetricModel();
		metricModel.setId(1);
		metricModel.setName("project version");
		metricModel.setValue("#get{project/properties/version}");
		metricModel.setType(AnalysisType.GIT_XML);
		metrics.add(metricModel);

		List<ComponentUriModel> uris = new ArrayList<>();
		ComponentUriModel uriModel = new ComponentUriModel();
		uriModel.setId(new ComponentUriPk(0, "uri_name"));
		uris.add(uriModel);

		List<ComponentPropertyModel> properties = new ArrayList<ComponentPropertyModel>();
		properties.add(RwsTestUtils.componentProperty("xml_path"));

		ComponentTypeArchitectureModel componentTypeArchitecture = new ComponentTypeArchitectureModel();
		componentTypeArchitecture.setId(1);
		componentTypeArchitecture.setMetrics(metrics);

		ComponentModel component = new ComponentModel();
		component.setId(1);
		component.setComponentTypeArchitecture(componentTypeArchitecture);
		component.setUris(uris);
		component.setProperties(properties);

		when(componentTypeArchitectureMetricPropertiesRep.findByComponentTypeArchitectureAndMetricAndNameIgnoreCase(
				any(ComponentTypeArchitectureModel.class), any(MetricModel.class), anyString()))
				.thenReturn(Optional.of(RwsTestUtils.componetTypeArchitectureMetricPropertiesModelMock()));
		when(gitClient.repoFile(any(URI.class))).thenReturn(
				RwsTestUtils.response(200, RwsTestUtils.gitContentResponse(RwsTestUtils.XML_RESPONSE_TEST)));

		List<ComponentAnalysisModel> analysis = service.analyze("workflowId", component);

		assertEquals(1, analysis.size());
		assertEquals("1", analysis.get(0).getMetricValue());
	}

}
