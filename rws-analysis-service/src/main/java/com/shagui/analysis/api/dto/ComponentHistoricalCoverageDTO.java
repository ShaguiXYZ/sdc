package com.shagui.analysis.api.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComponentHistoricalCoverageDTO {
	private Date componentAnalysisDate;
	private float coverage;
}
