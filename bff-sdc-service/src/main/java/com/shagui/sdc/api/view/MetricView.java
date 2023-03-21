package com.shagui.sdc.api.view;

import com.shagui.sdc.enums.MetricType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetricView {
	private Integer id;
	private String name;
	private MetricType type;
}
