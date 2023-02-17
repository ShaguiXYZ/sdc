package com.shagui.analysis.api.dto.sonar;

import lombok.Data;

@Data
public class MeasureSonarDTO {
	private String metric;
	private String value;
	private String component;
}
