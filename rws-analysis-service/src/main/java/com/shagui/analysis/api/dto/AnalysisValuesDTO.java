package com.shagui.analysis.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnalysisValuesDTO {
	private String metricValue;
	private String expectedValue;
	private String goodValue;
	private String perfectValue;
}
