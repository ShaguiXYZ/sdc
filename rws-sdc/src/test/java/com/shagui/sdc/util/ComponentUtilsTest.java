package com.shagui.sdc.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.test.utils.RwsTestUtils;

public class ComponentUtilsTest {
	
	@BeforeEach
	void init(){
	    MockitoAnnotations.openMocks(this);
	    
	    ComponentUtils.setConfig(RwsTestUtils.config());
	}

	@Test
	void setConfigTest() {
		
		ComponentUtils.setConfig(RwsTestUtils.config());
		
		
	}
}

