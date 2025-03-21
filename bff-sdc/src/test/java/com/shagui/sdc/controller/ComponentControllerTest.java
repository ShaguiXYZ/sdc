package com.shagui.sdc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.domain.HistoricalCoverage;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.view.ComponentView;
import com.shagui.sdc.api.view.MetricView;
import com.shagui.sdc.service.ComponentService;
import com.shagui.sdc.test.utils.DataUtils;
import com.shagui.sdc.test.utils.DtoDataUtils;

class ComponentControllerTest {

	@InjectMocks
	ComponentController componentController;

	@Mock
	ComponentService componentService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void componentTest() {
		ComponentDTO dto = DtoDataUtils.createComponent();
		when(componentService.findBy(anyInt())).thenReturn(dto);
		ComponentView result = componentController.component(DataUtils.DEFAULT_COMPONENT_ID);
		assertNotNull(result);
		assertEquals(result.getId(), dto.getId());
		assertEquals(result.getName(), dto.getName());
	}

	@Test
	void squadComponentsTest() {
		when(componentService.squadComponents(anyInt(), any(), any())).thenReturn(DataUtils.createEmptyPageData());
		PageData<ComponentView> result = componentController.squadComponents(DataUtils.DEFAULT_SQUAD_ID, null, null);
		assertNotNull(result);
	}

	@Test
	void filterTest() {
		when(componentService.filter(anyString(), anyInt(), anySet(), anyFloat(), anyFloat(),anyInt(), anyInt())).thenReturn(DataUtils.createEmptyPageData());
		PageData<ComponentView> result = componentController.filter("name", 1, new HashSet<>(), 1f, 2f, 0, 10);
		assertNotNull(result);		
	}

	@Test
	void componentMetricsTest() {
		when(componentService.componentMetrics(anyInt())).thenReturn(DataUtils.createEmptyPageData());
		PageData<MetricView> result = componentController.componentMetrics(DataUtils.DEFAULT_COMPONENT_ID);
		assertNotNull(result);		
	}

	@Test
	void historicalTest() {
		when(componentService.historical(anyInt(), any(), any())).thenReturn(DataUtils.createEmptyHistoricalCoverage(DtoDataUtils.createComponent()));
		HistoricalCoverage<ComponentView> result = componentController.historical(DataUtils.DEFAULT_COMPONENT_ID, null, null);
		assertNotNull(result);		
	}
}
