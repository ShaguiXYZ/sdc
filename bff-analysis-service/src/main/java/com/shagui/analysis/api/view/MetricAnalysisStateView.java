package com.shagui.analysis.api.view;

import java.util.List;

import lombok.Data;

@Data
public class MetricAnalysisStateView {
	private List<MetricAnalysisView> metricAnalysis;
	private float coverage;
}
