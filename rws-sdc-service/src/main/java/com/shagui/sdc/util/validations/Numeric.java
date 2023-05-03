package com.shagui.sdc.util.validations;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Numeric implements Comparable<Numeric> {
	private Float value;

	public Numeric(String value) {
		this.value = cast(value);
	}

	@Override
	public int compareTo(Numeric o) {
		return value.compareTo(o.value);
	}

	private Float cast(String toCast) {
		return Float.valueOf(toCast);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
