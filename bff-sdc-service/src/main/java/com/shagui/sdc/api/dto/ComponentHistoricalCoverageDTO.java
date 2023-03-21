package com.shagui.sdc.api.dto;

import java.util.Date;

import com.shagui.sdc.api.view.ComponentHistoricalCoverageView;
import com.shagui.sdc.api.view.ParseableTo;
import com.shagui.sdc.util.Mapper;

import lombok.Data;

@Data
public class ComponentHistoricalCoverageDTO implements ParseableTo<ComponentHistoricalCoverageView> {
	private Date analysisDate;
	private float coverage;
	
	@Override
	public ComponentHistoricalCoverageView parse() {
		return Mapper.parse(this);
	}
}
