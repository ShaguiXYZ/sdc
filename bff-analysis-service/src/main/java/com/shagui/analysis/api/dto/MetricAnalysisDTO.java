package com.shagui.analysis.api.dto;

import java.util.Date;

import com.shagui.analysis.api.view.MetricAnalysisView;
import com.shagui.analysis.api.view.ParseableTo;
import com.shagui.analysis.enums.MetricState;
import com.shagui.analysis.util.Mapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricAnalysisDTO  implements ParseableTo<MetricAnalysisView> {
	private Date analysisDate;
	private MetricDTO metric;
	private AnalysisValuesDTO analysisValues;
	private MetricState state;
	
	@Override
	public MetricAnalysisView parse() {
		return Mapper.parse(this);
	}
}
