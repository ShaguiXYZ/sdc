package com.shagui.analysis.api.dto;

import com.shagui.analysis.enums.MetricType;
import com.shagui.analysis.enums.MetricValidation;
import com.shagui.analysis.enums.MetricValueType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricDTO {
	private Integer id;
	private String name;
	private MetricType type;
	private MetricValueType valueType;
	private MetricValidation validation;
}
