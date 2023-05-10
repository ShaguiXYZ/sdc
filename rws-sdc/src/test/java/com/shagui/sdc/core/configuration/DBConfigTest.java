package com.shagui.sdc.core.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class DBConfigTest {
	
	@InjectMocks
	private DBConfig config;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);

	}

}
