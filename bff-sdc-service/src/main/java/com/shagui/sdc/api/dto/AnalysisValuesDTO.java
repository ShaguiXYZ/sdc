package com.shagui.sdc.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnalysisValuesDTO {
	private String metricValue;
	private String expectedValue;
	private String goodValue;
	private String perfectValue;
}
