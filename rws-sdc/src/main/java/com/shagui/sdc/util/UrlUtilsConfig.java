package com.shagui.sdc.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Component
@AllArgsConstructor
public class UrlUtilsConfig {
	@Getter
	private final ObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		UrlUtils.setConfig(this);
	}
}
