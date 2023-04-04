package com.shagui.sdc.api.dto;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.view.MetricView;
import com.shagui.sdc.enums.MetricType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricDTO {
	private Integer id;
	private String name;
	private MetricType type;
	private MetricValidation validation;
	private MetricValueType valueType;

	public MetricDTO(MetricView source) {
		BeanUtils.copyProperties(source, this);
	}
}
