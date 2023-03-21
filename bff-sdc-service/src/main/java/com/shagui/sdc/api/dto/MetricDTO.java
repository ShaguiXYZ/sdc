package com.shagui.sdc.api.dto;

import com.shagui.sdc.enums.MetricType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricDTO {
	private Integer id;
	private String name;
	private MetricType type;
}
