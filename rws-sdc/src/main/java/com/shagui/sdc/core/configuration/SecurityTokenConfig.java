package com.shagui.sdc.core.configuration;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "sdc.security")
public class SecurityTokenConfig {
	private final Map<String, String> secret;

	public Map<String, String> dictionary() {
		return secret;
	}
	
	public String secret(String key) {
		return secret.get(key);
	}
}
