package com.shagui.analysis.api.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentStateDTO {
	private List<MetricAnalysisDTO> metricAnalysis;
	private float coverage;
}
