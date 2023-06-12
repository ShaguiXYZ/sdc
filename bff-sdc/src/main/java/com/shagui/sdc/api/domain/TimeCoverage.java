package com.shagui.sdc.api.domain;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TimeCoverage {
	private Float coverage;
	private Date analysisDate;

	public TimeCoverage(TimeCoverage source) {
		this.coverage = source.getCoverage();
		this.analysisDate = source.getAnalysisDate();
	}
}
