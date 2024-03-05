package com.shagui.sdc.enums;

import com.shagui.sdc.util.validations.types.Bool;
import com.shagui.sdc.util.validations.types.Numeric;
import com.shagui.sdc.util.validations.types.NumericMap;
import com.shagui.sdc.util.validations.types.Version;

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
