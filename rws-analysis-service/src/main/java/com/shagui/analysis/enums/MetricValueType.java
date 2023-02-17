package com.shagui.analysis.enums;

import java.util.function.Function;

import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.util.MetricValidations;

public enum MetricValueType {
	NUMERIC(MetricValidations.validateNumeric), 
	VERSION(MetricValidations.validateVersion);
	
	private Function<MetricAnalysisDTO, MetricState> fn;
	private MetricValueType(Function<MetricAnalysisDTO, MetricState> fn) {
		this.fn = fn;
	}
	
	public MetricState validate(MetricAnalysisDTO value) {
		return value.getMetric().getValueType().fn.apply(value);
	}
}
