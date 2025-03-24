package com.shagui.sdc.enums;

import com.shagui.sdc.util.validations.types.Bool;
import com.shagui.sdc.util.validations.types.Numeric;
import com.shagui.sdc.util.validations.types.NumericMap;
import com.shagui.sdc.util.validations.types.Version;

/**
 * Enum representing different types of metric values.
 * Each enum constant is associated with a specific validation class.
 * 
 * <ul>
 * <li>BOOLEAN - {@link Bool}</li>
 * <li>NUMERIC - {@link Numeric}</li>
 * <li>NUMERIC_MAP - {@link NumericMap}</li>
 * <li>VERSION - {@link Version}</li>
 * </ul>
 */
public enum MetricValueType {
	BOOLEAN(Bool.class), NUMERIC(Numeric.class), NUMERIC_MAP(NumericMap.class), VERSION(Version.class);

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
