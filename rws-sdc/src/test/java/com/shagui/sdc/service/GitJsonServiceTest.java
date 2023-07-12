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
import com.shagui.sdc.service.impl.GitJsonServiceImpl;
import com.shagui.sdc.test.utils.RwsTestUtils;
import com.shagui.sdc.util.UrlUtils;

class GitJsonServiceTest {

	@InjectMocks
	GitJsonServiceImpl service;

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
		StaticRepository.setConfig(staticRepositoryConfig);

		UrlUtils.setConfig(RwsTestUtils.urlUtilsConfig());
		when(staticRepositoryConfig.uris()).thenReturn(new ArrayList<>() {
			private static final long serialVersionUID = 1L;

			{
				add(RwsTestUtils.uriModelMock());
			}
		});
	}

	@Test
	void testAnalyzeJsonMetricsNotEmpty() throws JsonParseException, JsonMappingException, IOException {

		List<MetricModel> metrics = new ArrayList<MetricModel>();
		MetricModel metricModel = new MetricModel();
		metricModel.setId(1);
		metricModel.setName("project id");
		metricModel.setValue("id");
		metricModel.setType(AnalysisType.GIT_JSON);
		metrics.add(metricModel);

		List<ComponentUriModel> uris = new ArrayList<>();
		ComponentUriModel uriModel = new ComponentUriModel();
		uriModel.setId(new ComponentUriPk(0, "uri_name"));
		uris.add(uriModel);

		List<ComponentPropertyModel> properties = new ArrayList<ComponentPropertyModel>();
		ComponentPropertyModel componentProperty = new ComponentPropertyModel();
		componentProperty.setName("test");
		properties.add(componentProperty);

		ComponentTypeArchitectureModel componentTypeArchitecture = new ComponentTypeArchitectureModel();
		componentTypeArchitecture.setId(1);
		componentTypeArchitecture.setMetrics(metrics);

		ComponentModel component = new ComponentModel();
		component.setId(1);
		component.setComponentTypeArchitecture(componentTypeArchitecture);
		component.setUris(uris);
		component.setProperties(properties);

		when(componentTypeArchitectureMetricPropertiesRep.findByComponentTypeArchitectureAndMetricAndName(
				any(ComponentTypeArchitectureModel.class), any(MetricModel.class), anyString()))
				.thenReturn(Optional.of(RwsTestUtils.componetTypeArchitectureMetricPropertiesModelMock()));
		when(gitClient.repoFile(any(URI.class))).thenReturn(
				RwsTestUtils.response(200, RwsTestUtils.gitContentResponse(RwsTestUtils.JSON_RESPONSE_TEST)));

		List<ComponentAnalysisModel> analysis = service.analyze(component);

		assertEquals(1, analysis.size());
		assertEquals("100", analysis.get(0).getValue());
	}

}
