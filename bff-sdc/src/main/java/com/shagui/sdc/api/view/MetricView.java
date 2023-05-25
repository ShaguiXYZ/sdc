package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricView {
	private Integer id;
	private String name;
	private AnalysisType type;
	private MetricValidation validation;
	private MetricValueType valueType;
	
	public MetricView(MetricDTO source) {
		BeanUtils.copyProperties(source, this);
	}
}
