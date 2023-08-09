package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.client.GitClient;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.StaticRepositoryConfig;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.service.impl.DependabotServiceImpl;
import com.shagui.sdc.test.utils.RwsTestUtils;
import com.shagui.sdc.util.UrlUtils;

class DependabotServiceImplTest {

	@InjectMocks
	DependabotServiceImpl service;

	@Mock
	private GitClient gitClient;

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
				add(RwsTestUtils.uriModelMock(UriType.DEPENDABOT));
			}
		});
	}

	@Test
	void analyzeTest() {
		when(gitClient.repoFile(any(URI.class))).thenReturn(
				RwsTestUtils.response(200, RwsTestUtils.JSON_DEPENDABOT_CONTENT));

		List<ComponentAnalysisModel> result = service.analyze(RwsTestUtils.componentModelMock());
		assertEquals(1, result.size());
	}

}
