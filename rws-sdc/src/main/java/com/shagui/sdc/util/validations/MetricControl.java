package com.shagui.sdc.util.validations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MetricControl<T extends Comparable<T>> {
	private T control;
	private Float coverage;
}
