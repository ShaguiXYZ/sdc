package com.shagui.sdc.exception;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.shagui.sdc.api.ComponentRestApi;
import com.shagui.sdc.core.exception.CustomExceptionHandler;
import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.service.SseService;

import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;

class CustomExceptionHandlerTest {
	private MockMvc mockMvc;

	@Mock
	private SseService sseService;

	@Mock
	private ComponentRestApi controller;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new CustomExceptionHandler(sseService))
				.build();
	}

	@Test
	void genericExectionHandlerTest() throws Exception {
		when(controller.component(anyInt())).thenThrow(new RuntimeException("Unexpected Exception"));

		mockMvc.perform(get("/api/component/1"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	void jpaNotFoundExceptionHandlerTest() throws Exception {
		when(controller.component(anyInt())).thenThrow(new JpaNotFoundException("JPA Exception"));

		mockMvc.perform(get("/api/component/1"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	void feignExceptionHandlerTest() throws Exception {
		Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), null, new RequestTemplate());
		FeignException ex = new FeignException.NotFound("Unexpected Exception", request, null, null);
		when(controller.component(anyInt())).thenThrow(ex);

		mockMvc.perform(get("/api/component/1")).andDo(print()).andExpect(status().isNotFound());
	}

}
