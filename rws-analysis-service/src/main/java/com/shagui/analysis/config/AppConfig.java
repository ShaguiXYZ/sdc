package com.shagui.analysis.config;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;

public class AppConfig {
	@Getter
	@Value("${services.xml.default-xml-path}")
	private String defaultXmlPath;
}
