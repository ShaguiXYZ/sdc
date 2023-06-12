package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.dto.AnalysisValuesDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
