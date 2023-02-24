package com.shagui.analysis.api.dto.sonar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasureSonarDTO {
	private String metric;
	private String value;
	private String component;
}
