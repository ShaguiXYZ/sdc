package com.shagui.analysis.api.view;

import java.util.List;

import lombok.Data;

@Data
public class ComponentStateView {
	private List<MetricAnalysisView> metricAnalysis;
	private float coverage;
}
