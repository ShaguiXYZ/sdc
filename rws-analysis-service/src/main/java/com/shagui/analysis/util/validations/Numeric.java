package com.shagui.analysis.util.validations;

public class Numeric implements Comparable<Numeric> {
	private Float value;

	public Numeric(String vlaue) {
		this.value = cast(vlaue);
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
