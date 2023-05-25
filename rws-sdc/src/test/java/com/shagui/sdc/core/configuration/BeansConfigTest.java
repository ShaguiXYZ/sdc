package com.shagui.sdc.core.configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;

class BeansConfigTest {

	@InjectMocks
	private BeansConfig config;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getObjectMapperTest() {
		ObjectMapper result = config.getObjectMapper();
		assertNotNull(result);
	}
}
