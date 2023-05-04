package com.shagui.sdc.enums;

public enum MetricState {
	CRITICAL(10f), WITH_RISK(50f), ACCEPTABLE(75f), PERFECT(100f);
	
	private Float coverage;
	
	MetricState(Float coverage) {
		this.coverage = coverage;
	}
	
	public Float coverage() {
		return coverage;
	}
}
