package com.shagui.analysis.api.dto.sonar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentSonarDTO {
	private String key;
	private String name;
	private String qualifier;
	private String project;
}
