package com.shagui.sdc.api.dto;

import java.util.Date;

import com.shagui.sdc.api.view.MetricAnalysisView;
import com.shagui.sdc.api.view.ParseableTo;
import com.shagui.sdc.util.Mapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricAnalysisDTO  implements ParseableTo<MetricAnalysisView> {
	private Date analysisDate;
	private MetricDTO metric;
	private AnalysisValuesDTO analysisValues;
	private Float coverage;
	
	@Override
	public MetricAnalysisView parse() {
		return Mapper.parse(this);
	}
}
