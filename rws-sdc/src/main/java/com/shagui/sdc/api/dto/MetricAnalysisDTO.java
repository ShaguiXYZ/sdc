package com.shagui.sdc.api.dto;

import java.util.Date;

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
	private Float coverage;
	private boolean blocker;
}
