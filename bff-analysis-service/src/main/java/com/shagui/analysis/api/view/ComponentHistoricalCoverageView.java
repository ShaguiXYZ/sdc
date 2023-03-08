package com.shagui.analysis.api.view;

import java.util.Date;

import lombok.Data;

@Data
public class ComponentHistoricalCoverageView {
	private Date componentAnalysisDate;
	private float coverage;
}
