package com.shagui.sdc.util.validations;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetricControl<T extends Comparable<T>> {
	private T control;
	private Float coverage;
}
