package com.shagui.analysis.api.view;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnalysisValuesView {
	private String metricValue;
	private String expectedValue;
	private String goodValue;
	private String perfectValue;
}
