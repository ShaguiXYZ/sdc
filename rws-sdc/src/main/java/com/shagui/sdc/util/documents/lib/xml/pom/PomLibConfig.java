package com.shagui.sdc.util.documents.lib.xml.pom;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import({ PomProperties.class })
public class PomLibConfig {
	private final PomProperties properties;

	public PomLibConfig(PomProperties properties) {
		this.properties = properties;
	}

	public PomProperties properties() {
		return properties;
	}

	@PostConstruct
	public void init() {
		PomLib.setConfig(this);
	}
}
