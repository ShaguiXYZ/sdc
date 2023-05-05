package com.shagui.sdc.core.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DBConfigTest {
	
	@Mock
	private DBConfig config;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	void test() {
		
		config.dataSource();
		
	}

}
