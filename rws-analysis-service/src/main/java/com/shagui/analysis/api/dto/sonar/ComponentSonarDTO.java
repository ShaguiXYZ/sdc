package com.shagui.analysis.api.dto.sonar;

import lombok.Data;

@Data
public class ComponentSonarDTO {
	private String key;
	private String name;
	private String qualifier;
	private String project;
}
