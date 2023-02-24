package com.shagui.analysis.api.dto;

import java.util.Date;

import com.shagui.analysis.enums.MetricState;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricAnalysisDTO {
	private Date analysisDate;
	private MetricDTO metric;
	private AnalysisValuesDTO analysisValues;
	private MetricState state;
}
