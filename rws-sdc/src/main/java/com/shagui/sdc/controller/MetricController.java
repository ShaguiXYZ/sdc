package com.shagui.sdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.MetricRestApi;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;
import com.shagui.sdc.service.MetricService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "metrics", description = "API to maintain Metrics")
public class MetricController implements MetricRestApi {

	@Autowired
	private MetricService metricService;

	@Override
	public MetricDTO create(MetricDTO metric) {
		return metricService.create(metric);
	}

	@Override
	public MetricDTO createValues(String name, String description, AnalysisType type, MetricValueType valueType,
			MetricValidation validation, String value) {
		return create(new MetricDTO(null, name, value, description, type, valueType, validation));
	}

	@Override
	public MetricDTO update(int metricId, MetricDTO metric) {
		return metricService.update(metricId, metric);
	}

}
