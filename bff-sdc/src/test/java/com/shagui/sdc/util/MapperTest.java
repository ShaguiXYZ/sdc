package com.shagui.sdc.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shagui.sdc.core.exception.ApiError;

import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;

class MapperTest {

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void mapperFeignException() throws JsonProcessingException, JSONException {
		Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), null, new RequestTemplate());
		FeignException ex = new FeignException.NotFound("Unexpected Exception", request, null, null);
		ApiError result = Mapper.parse(ex);

		assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
		assertEquals("Unexpected Exception", result.getMessage());
	}
}
