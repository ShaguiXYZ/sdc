package com.shagui.sdc.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.service.MetricService;

class MetricControllerTest {
	
	@InjectMocks
	private MetricController controller;
	
	@Mock
	private MetricService metricService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createTest() {
		
		when(metricService.create(any(MetricDTO.class))).thenReturn(new MetricDTO());
		MetricDTO result = controller.create(new MetricDTO());
		assertNotNull(result);
	}
	
	@Test
	void updateTest() {
		
		when(metricService.update(anyInt(), any(MetricDTO.class))).thenReturn(new MetricDTO());
		MetricDTO result = controller.update(1, new MetricDTO());
		assertNotNull(result);
	}

	

}
