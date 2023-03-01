package com.shagui.analysis.api.dto;

import java.util.List;

import lombok.Data;

@Data
public class ComponentStateDTO {
	private List<MetricAnalysisDTO> metricAnalysis;
	private float coverage;
}
