package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.dto.AnalysisValuesDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnalysisValuesView {
	private String metricValue;
	private String expectedValue;
	private String goodValue;
	private String perfectValue;

	public AnalysisValuesView(AnalysisValuesDTO source) {
		BeanUtils.copyProperties(source, this);
	}
}
