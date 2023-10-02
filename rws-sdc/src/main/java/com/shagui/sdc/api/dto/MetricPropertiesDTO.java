package com.shagui.sdc.api.dto;

import java.util.Map;

import com.shagui.sdc.enums.AnalysisType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricPropertiesDTO {
	private String metricName;
	private AnalysisType type;
	private Map<String, String> params;
}
