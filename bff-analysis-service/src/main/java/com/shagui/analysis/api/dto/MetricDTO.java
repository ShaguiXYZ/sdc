package com.shagui.analysis.api.dto;

import com.shagui.analysis.enums.MetricType;

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
}
