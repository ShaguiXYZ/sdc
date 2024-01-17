package com.shagui.sdc.api.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import com.shagui.sdc.model.MetricValuesModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MetricValuesDTO {
	private int weight;
	private String value;
	private String goodValue;
	private String perfectValue;

	public MetricValuesDTO(@NonNull MetricValuesModel model) {
		BeanUtils.copyProperties(model, this);
	}
}
