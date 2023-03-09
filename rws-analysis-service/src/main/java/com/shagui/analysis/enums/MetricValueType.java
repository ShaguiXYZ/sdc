package com.shagui.analysis.enums;

import com.shagui.analysis.util.validations.Numeric;
import com.shagui.analysis.util.validations.Version;

public enum MetricValueType {
	NUMERIC(Numeric.class), VERSION(Version.class);

	@SuppressWarnings("rawtypes")
	private Class clazz;

	private <T extends Comparable<T>> MetricValueType(Class<T> clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("rawtypes")
	public Class clazz() {
		return clazz;
	}
}
