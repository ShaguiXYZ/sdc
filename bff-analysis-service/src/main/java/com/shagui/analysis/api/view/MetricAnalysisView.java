package com.shagui.analysis.api.view;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricAnalysisView {
	private Date analysisDate;
	private MetricView metric;
	private AnalysisValuesView analysisValues;
	private Float coverage;
}
