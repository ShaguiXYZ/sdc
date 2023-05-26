package com.shagui.sdc.util;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MapperConfig {
	private final ObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		Mapper.setConfig(this);
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
}
