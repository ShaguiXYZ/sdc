package com.shagui.sdc.api.dto;

import com.shagui.sdc.model.MetricValuesModel;
import com.shagui.sdc.util.Mapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MetricValuesOutDTO extends MetricValuesDTO {
	private MetricDTO metric;
	private ComponentTypeArchitectureDTO componentTypeArchitecture;

	public MetricValuesOutDTO(MetricValuesModel model) {
		super(model);
		
		this.metric = Mapper.parse(model.getMetric());
		this.componentTypeArchitecture = Mapper.parse(model.getComponentTypeArchitecture());
	}
}
