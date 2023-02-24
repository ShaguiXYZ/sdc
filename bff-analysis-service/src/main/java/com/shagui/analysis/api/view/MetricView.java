package com.shagui.analysis.api.view;

import com.shagui.analysis.enums.MetricType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricView {
	private Integer id;
	private String name;
	private MetricType type;
}
