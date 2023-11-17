package com.shagui.sdc.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.MetricRestApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;
import com.shagui.sdc.service.MetricService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "metrics", description = "API to maintain Metrics")
public class MetricController implements MetricRestApi {
	private MetricService metricService;

	@Override
	public MetricDTO metric(int metricId) {
		return metricService.metric(metricId);
	}

	@Override
	public PageData<MetricDTO> metrics() {
		return metricService.metrics();
	}

	@Override
	public MetricDTO create(MetricDTO metric) {
		return metricService.create(metric);
	}

	@Override
	public MetricDTO createValues(String name, String description, AnalysisType type, MetricValueType valueType,
			MetricValidation validation, String value, boolean blocker) {
		return create(new MetricDTO(null, name, value, description, type, valueType, validation, blocker));
	}

	@Override
	public MetricDTO update(int metricId, MetricDTO metric) {
		return metricService.update(metricId, metric);
	}
}
