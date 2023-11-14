package com.shagui.sdc.util;

import org.springframework.stereotype.Component;

import com.shagui.sdc.model.MetricValuesModel;
import com.shagui.sdc.repository.MetricValueRepository;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AnalysisUtilsConfig {
	private MetricValueRepository metricValuesRepository;

	public JpaCommonRepository<MetricValueRepository, MetricValuesModel, Integer> metricValuesRepository() {
		return () -> metricValuesRepository;
	}

	@PostConstruct
	public void init() {
		AnalysisUtils.setConfig(this);
	}

}
