package com.shagui.sdc.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UrlUtilsConfig {
	private final ObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		UrlUtils.setConfig(this);
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
}
