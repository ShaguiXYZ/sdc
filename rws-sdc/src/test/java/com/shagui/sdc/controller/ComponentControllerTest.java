package com.shagui.sdc.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.Range;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.api.dto.ebs.ComponentInput;
import com.shagui.sdc.service.ComponentService;
import com.shagui.sdc.service.DataMaintenanceService;

class ComponentControllerTest {

	@InjectMocks
	private ComponentController controller;

	@Mock
	private AnalysisController analysisController;

	@Mock
	private ComponentService componentService;

	@Mock
	private DataMaintenanceService dataMaintenanceService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void componentTest() {
		ComponentDTO value = new ComponentDTO();
		when(componentService.findBy(anyInt())).thenReturn(value);
		ComponentDTO result = controller.component(1);
		assertNotNull(result);
	}

	@Test
	void patchTest() {
		ComponentInput value = new ComponentInput();
		ComponentDTO dto = new ComponentDTO();
		dto.setId(1);

		when(dataMaintenanceService.componentUpdateData(any(ComponentInput.class))).thenReturn(dto);
		when(analysisController.analyze(anyInt())).thenReturn(null);
		when(componentService.findBy(anyInt())).thenReturn(dto);

		ComponentDTO result = controller.patch(value);
		assertNotNull(result);
	}

	@Test
	void filterPageNullTest() {
		PageData<ComponentDTO> value = new PageData<ComponentDTO>(new ArrayList<ComponentDTO>());
		when(componentService.filter(anyString(), anyInt(), anySet(), any(Range.class))).thenReturn(value);
		PageData<ComponentDTO> result = controller.filter("test", 1, new HashSet<>(), (float) 50.1, (float) 70.1,
				null,
				1);
		assertNotNull(result);
	}

	@Test
	void filterWithPageTest() {
		PageData<ComponentDTO> value = new PageData<ComponentDTO>(new ArrayList<ComponentDTO>());
		when(componentService.filter(anyString(), anyInt(), anySet(), any(Range.class), any(RequestPageInfo.class)))
				.thenReturn(value);
		PageData<ComponentDTO> result = controller.filter("test", 1, new HashSet<>(), (float) 50.1, (float) 70.1, 1,
				1);
		assertNotNull(result);
	}

	@Test
	void squadComponentsNullTest() {
		PageData<ComponentDTO> value = new PageData<ComponentDTO>(new ArrayList<ComponentDTO>());
		when(componentService.squadComponents(anyInt())).thenReturn(value);
		PageData<ComponentDTO> result = controller.squadComponents(1, null, 1);
		assertNotNull(result);
	}

	@Test
	void squadComponentsNotNullTest() {
		PageData<ComponentDTO> value = new PageData<ComponentDTO>(new ArrayList<ComponentDTO>());
		when(componentService.squadComponents(anyInt(), any(RequestPageInfo.class))).thenReturn(value);
		PageData<ComponentDTO> result = controller.squadComponents(1, 1, 1);
		assertNotNull(result);
	}

	@Test
	void componentMetricsTest() {
		PageData<MetricDTO> value = new PageData<MetricDTO>(new ArrayList<MetricDTO>());
		when(componentService.componentMetrics(anyInt())).thenReturn(value);
		PageData<MetricDTO> result = controller.componentMetrics(1);
		assertNotNull(result);
	}

}
