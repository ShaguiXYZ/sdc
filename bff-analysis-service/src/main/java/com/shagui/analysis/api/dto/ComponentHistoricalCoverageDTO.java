package com.shagui.analysis.api.dto;

import java.util.Date;

import com.shagui.analysis.api.view.ComponentHistoricalCoverageView;
import com.shagui.analysis.api.view.ParseableTo;
import com.shagui.analysis.util.Mapper;

import lombok.Data;

@Data
public class ComponentHistoricalCoverageDTO implements ParseableTo<ComponentHistoricalCoverageView> {
	private Date componentAnalysisDate;
	private float coverage;
	
	@Override
	public ComponentHistoricalCoverageView parse() {
		return Mapper.parse(this);
	}
}
