package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.dto.MetricDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MetricView {
	private Integer id;
	private String name;
	private String type;
	private String validation;
	private String valueType;

	public MetricView(MetricDTO source) {
		BeanUtils.copyProperties(source, this);
	}
}
