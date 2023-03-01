package com.shagui.analysis.api.dto;

import java.util.Date;

import com.shagui.analysis.enums.MetricState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MetricAnalysisDTO {
	private Date analysisDate;
	private MetricDTO metric;
	private AnalysisValuesDTO analysisValues;
	private MetricState state;
}
