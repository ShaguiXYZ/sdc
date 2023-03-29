package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.enums.MetricType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricView {
	private Integer id;
	private String name;
	private MetricType type;
	
	public MetricView(MetricDTO source) {
		BeanUtils.copyProperties(source, this);
	}
}
