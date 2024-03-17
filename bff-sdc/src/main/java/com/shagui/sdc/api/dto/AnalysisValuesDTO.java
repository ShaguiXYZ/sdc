package com.shagui.sdc.api.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import com.shagui.sdc.api.view.AnalysisValuesView;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnalysisValuesDTO {
	private String metricValue;
	private String expectedValue;
	private String goodValue;
	private String perfectValue;

	public AnalysisValuesDTO(@NonNull AnalysisValuesView source) {
		BeanUtils.copyProperties(source, this);
	}
}
