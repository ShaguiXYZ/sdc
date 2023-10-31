package com.shagui.sdc.enums;

import com.shagui.sdc.util.validations.Bool;
import com.shagui.sdc.util.validations.Numeric;
import com.shagui.sdc.util.validations.NumericMap;
import com.shagui.sdc.util.validations.Version;

public enum MetricValueType {
	NUMERIC(Numeric.class), NUMERIC_MAP(NumericMap.class), VERSION(Version.class), BOOLEAN(Bool.class);

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
