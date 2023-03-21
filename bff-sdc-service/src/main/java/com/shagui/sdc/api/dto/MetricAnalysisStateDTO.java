package com.shagui.sdc.api.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricAnalysisStateDTO {
	private List<MetricAnalysisDTO> metricAnalysis;
	private float coverage;
}
