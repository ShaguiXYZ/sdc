package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.StaticRepositoryConfig;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.service.impl.GitServiceImpl;
import com.shagui.sdc.test.utils.ReflectUtils;
import com.shagui.sdc.util.ComponentUtilsConfig;
import com.shagui.sdc.util.git.GitUtils;
import com.shagui.sdc.util.git.GitUtilsConfig;

class GitServiceImplTest {

	private GitService gitService;

	@Mock
	private ComponentUtilsConfig componentConfig;

	@Mock
	private SseService sseService;

	@Mock
	private GitUtilsConfig gitConfig;

	@Mock
	private StaticRepositoryConfig staticRepositoryConfig;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		gitService = new GitServiceImpl(sseService);

		when(staticRepositoryConfig.uris()).thenReturn(new ArrayList<>());
		when(staticRepositoryConfig.datalists()).thenReturn(new ArrayList<>());
		when(staticRepositoryConfig.componentParams()).thenReturn(new ArrayList<>());

		ReflectUtils.invoke(GitUtils.class, "setConfig", gitConfig);
		ReflectUtils.invoke(StaticRepository.class, "setConfig", staticRepositoryConfig);
	}

	@Test
	void analyze() {
		MetricModel metric = mock(MetricModel.class);
		when(metric.getValue()).thenReturn("ncloc_language_distribution");
		when(metric.getType()).thenReturn(AnalysisType.GIT);

		ComponentTypeArchitectureModel componentTypeArchitectureModel = mock(ComponentTypeArchitectureModel.class);
		when(componentTypeArchitectureModel.getId()).thenReturn(1);
		when(componentTypeArchitectureModel.getMetrics()).thenReturn(new ArrayList<>() {
			{
				add(metric);
			}
		});

		ComponentModel component = mock(ComponentModel.class);
		when(component.getId()).thenReturn(1);
		when(component.getName()).thenReturn("Component 1");
		when(component.getComponentTypeArchitecture()).thenReturn(componentTypeArchitectureModel);

		List<MetricModel> metrics = new ArrayList<>();
		metrics.add(metric);

		List<ComponentAnalysisModel> analysisList = gitService.analyze("workflowId", component);

		assertEquals(1, analysisList.size());
		ComponentAnalysisModel analysis = analysisList.get(0);
		assertEquals(component, analysis.getComponent());
		assertEquals(metric, analysis.getMetric());
		assertEquals("", analysis.getMetricValue());
	}
}