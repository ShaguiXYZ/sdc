package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.client.SonarClient;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.StaticRepositoryConfig;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.service.impl.SonarServiceImpl;
import com.shagui.sdc.test.utils.ReflectUtils;
import com.shagui.sdc.test.utils.RwsTestUtils;
import com.shagui.sdc.util.UrlUtils;

class SonarServiceImplTest {

	@InjectMocks
	private SonarServiceImpl service;

	@Mock
	private SonarClient sonarClient;

	@Mock
	private StaticRepositoryConfig staticRepositoryConfig;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);

		ReflectUtils.invoke(StaticRepository.class, "setConfig", staticRepositoryConfig);
		ReflectUtils.invoke(UrlUtils.class, "setConfig", RwsTestUtils.urlUtilsConfig());

		when(staticRepositoryConfig.uris()).thenReturn(new ArrayList<>() {
			private static final long serialVersionUID = 1L;

			{
				add(RwsTestUtils.uriModelMock(UriType.SONAR));
			}
		});
	}

	@Test
	void analyzeTest() {
		when(sonarClient.measures(any(URI.class), anyString(), anyString())).thenReturn(
				RwsTestUtils.response(200, RwsTestUtils.JSON_SONAR_CONTENT));

		List<ComponentAnalysisModel> result = service.analyze("workflowId", RwsTestUtils.componentModelMock());
		assertEquals(new ArrayList<>(), result);
	}

}
