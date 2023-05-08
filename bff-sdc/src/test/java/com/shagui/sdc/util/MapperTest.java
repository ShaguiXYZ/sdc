package com.shagui.sdc.util;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;

class MapperTest {

	@InjectMocks
	private static MapperConfig config;
	
	@Mock
	ObjectMapper objectMapper;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void setConfigtest() {
		assertNotNull(config.getObjectMapper());
	}
}
