package com.shagui.analysis.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shagui.analysis.model.MetricValuesModel;
import com.shagui.analysis.repository.JpaCommonRepository;
import com.shagui.analysis.repository.MetricValueRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AnalysisUtilsConfig {
	@Autowired
	private MetricValueRepository metricValuesRepository;
	
	public JpaCommonRepository<MetricValueRepository, MetricValuesModel, Integer> metricValuesRepository() {
		return () -> metricValuesRepository;
	}
	
	@PostConstruct
	public void init() {
		AnalysisUtils.setConfig(this);
	}

}
