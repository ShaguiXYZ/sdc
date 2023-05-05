package com.shagui.sdc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.api.client.SonarClient;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.service.impl.SonarServiceImpl;
import com.shagui.sdc.test.utils.RwsTestUtils;

class SonarServiceImplTest {
	
	@InjectMocks
	private SonarServiceImpl service;
	
	@Mock
	private SonarClient sonarClient;
	
	@BeforeEach
	void init(){
		
	    MockitoAnnotations.openMocks(this);
	    
	}

	@Test
	void analyzeTest() {
		
		//when(sonarClient.measures(any(URI.class), anyString(), anyString())).thenReturn(RwsTestUtils.response(200, RwsTestUtils.JSON_RESPONSE_TEST));
		List<ComponentAnalysisModel> result = service.analyze(RwsTestUtils.componentModelSonarMock());
		assertEquals(new ArrayList<>(), result);
		
	}

}
