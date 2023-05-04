package com.shagui.sdc.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HistoricalCoverage<T> {
	private T data;
	private PageData<TimeCoverage> historical;
}
