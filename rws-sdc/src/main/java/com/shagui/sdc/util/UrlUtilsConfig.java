package com.shagui.sdc.util;

import org.springframework.stereotype.Component;
import org.springframework.web.util.pattern.PathPatternParser;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Component
@AllArgsConstructor
public class UrlUtilsConfig {
	@Getter
	private final ObjectMapper objectMapper;

	@Getter
	private final PathPatternParser pathPatternParser;

	@PostConstruct
	public void init() {
		UrlUtils.setConfig(this);
	}
}
