package com.shagui.sdc.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnalysisValuesDTO {
	private int weight;
	private String metricValue;
	private String expectedValue;
	private String goodValue;
	private String perfectValue;
}
