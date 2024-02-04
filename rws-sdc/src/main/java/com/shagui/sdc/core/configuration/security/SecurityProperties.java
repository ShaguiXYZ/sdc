package com.shagui.sdc.core.configuration.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "rest.security")
public class SecurityProperties {
	private boolean enabled;
	private List<ApiMatcher> apiMatcher = new ArrayList<>();
	private String issuerUri;
	private CorsConfiguration cors;

	public CorsConfiguration getCorsConfiguration() {
		log.debug("getCorsConfiguration");
		return cors;
	}

	public String[] allRegex() {
		return apiMatcher != null ? apiMatcher.stream().map(ApiMatcher::getApi).toArray(String[]::new) : new String[0];
	}

	public String[] publicRegex() {
		return apiMatcher != null ? apiMatcher.stream().filter(ApiMatcher::isPublic).map(ApiMatcher::getApi)
				.toArray(String[]::new) : new String[0];
	}

	@Getter()
	@Setter()
	public static class ApiMatcher {
		String api;
		boolean isPublic;
	}
}