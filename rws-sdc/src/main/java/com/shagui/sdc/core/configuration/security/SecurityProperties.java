package com.shagui.sdc.core.configuration.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "rest.security")
public class SecurityProperties {
	private boolean enabled;
	private String[] apiMatcher;
	private String issuerUri;
	private CorsConfiguration cors;

	public CorsConfiguration getCorsConfiguration() {
		log.debug("getCorsConfiguration");
		return cors;
	}
}