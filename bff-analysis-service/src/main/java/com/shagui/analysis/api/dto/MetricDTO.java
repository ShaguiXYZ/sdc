package com.shagui.analysis.api.dto;

import com.shagui.analysis.enums.MetricType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricDTO {
	private Integer id;
	private String name;
	private MetricType type;
}
