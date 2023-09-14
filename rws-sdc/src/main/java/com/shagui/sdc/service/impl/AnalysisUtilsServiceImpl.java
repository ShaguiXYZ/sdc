package com.shagui.sdc.service.impl;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.pk.ComponentAnalysisPk;
import com.shagui.sdc.repository.ComponentAnalysisRepository;
import com.shagui.sdc.repository.MetricRepository;
import com.shagui.sdc.service.AnalysisUtilsService;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

@Service
public class AnalysisUtilsServiceImpl implements AnalysisUtilsService {

	private JpaCommonRepository<ComponentAnalysisRepository, ComponentAnalysisModel, ComponentAnalysisPk> componentAnalysisRepository;
	private JpaCommonRepository<MetricRepository, MetricModel, Integer> metricRepository;

	public AnalysisUtilsServiceImpl(ComponentAnalysisRepository componentAnalysisRepository,
			MetricRepository metricRepository) {
		this.metricRepository = () -> metricRepository;
		this.componentAnalysisRepository = () -> componentAnalysisRepository;
	}

	@Override
	public Map<String, String> languageDistribution(int componentId) {
		return metricRepository.repository().findByValue("ncloc_language_distribution")
				.map(metric -> componentAnalysisRepository.repository().actualMetric(componentId, metric.getId())
						.map(componentAnalysis -> Pattern.compile("\\s*;\\s*")
								.splitAsStream(componentAnalysis.getValue()).map(s -> s.split("\\s*=\\s*"))
								.collect(Collectors.toMap(a -> a[0], a -> a.length > 1 ? a[1] : "0")))
						.orElseThrow(() -> new SdcCustomException(
								String.format("Not language distribution found for component %s.", componentId))))
				.orElseThrow(() -> new SdcCustomException(
						String.format("Not language distribution found for component %s.", componentId)));
	}
}
