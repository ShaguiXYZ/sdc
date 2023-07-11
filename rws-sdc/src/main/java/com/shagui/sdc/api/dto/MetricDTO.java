package com.shagui.sdc.api.dto;

import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetricDTO {
	private Integer id;
	private String name;
	private String value;
	private String description;
	private AnalysisType type;
	private MetricValueType valueType;
	private MetricValidation validation;
}
