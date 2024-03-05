package com.shagui.sdc.util.validations.types;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Bool implements Comparable<Bool> {
	private Boolean value = null;

	public Bool(String value) {
		this.value = Boolean.parseBoolean(value);
	}

	@Override
	public int compareTo(Bool o) {
		return value.compareTo(o.value);
	}
}
