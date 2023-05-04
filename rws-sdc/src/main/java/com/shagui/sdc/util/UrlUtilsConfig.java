package com.shagui.sdc.util;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UrlUtilsConfig {
	private ObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		UrlUtils.setConfig(this);
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
}
