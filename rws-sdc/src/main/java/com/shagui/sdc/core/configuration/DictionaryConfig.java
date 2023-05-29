package com.shagui.sdc.core.configuration;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "sdc.dictionary")
public class DictionaryConfig {
	private final Map<String, String> secret;

	public Map<String, String> secret() {
		return secret;
	}
}
