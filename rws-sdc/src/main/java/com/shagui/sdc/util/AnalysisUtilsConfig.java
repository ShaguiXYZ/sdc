package com.shagui.sdc.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shagui.sdc.model.MetricValuesModel;
import com.shagui.sdc.repository.MetricValueRepository;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

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
