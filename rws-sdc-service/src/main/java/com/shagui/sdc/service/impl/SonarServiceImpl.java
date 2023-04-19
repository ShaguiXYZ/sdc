package com.shagui.sdc.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.SonarClient;
import com.shagui.sdc.api.dto.sonar.MeasureSonarDTO;
import com.shagui.sdc.api.dto.sonar.MeasuresSonarDTO;
import com.shagui.sdc.enums.MetricType;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.UriModel;
import com.shagui.sdc.service.SonarService;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.UrlUtils;

import feign.Response;

@Service(Ctes.ANALYSIS_SERVICES_TYPES.SONAR)
public class SonarServiceImpl implements SonarService {
	@Autowired
	private SonarClient sonarClient;

	@Override
	public List<ComponentAnalysisModel> analyze(ComponentModel component) {
		List<ComponentAnalysisModel> analysisList = new ArrayList<>();
		List<MetricModel> sonarMetrics = component.getComponentTypeArchitecture().getMetrics().stream()
				.filter(metric -> MetricType.SONAR.equals(metric.getType())).collect(Collectors.toList());

		if (!sonarMetrics.isEmpty()) {
			Optional<MeasuresSonarDTO> measures = getSonarClientMeasures(component, sonarMetrics);

			if (measures.isPresent()) {
				component.getComponentTypeArchitecture().getMetrics().forEach(metric -> {
					Optional<MeasureSonarDTO> measure = measureByMetric(measures.get(), metric);

					if (measure.isPresent()) {
						analysisList.add(new ComponentAnalysisModel(component, metric, measure.get().getValue()));
					}
				});
			}
		}

		return analysisList;
	}

	private Optional<MeasureSonarDTO> measureByMetric(MeasuresSonarDTO measures, MetricModel metric) {
		return measures.getMeasures().stream().filter(m -> m.getMetric().equals(metric.getName())).findFirst();
	}

	private Optional<MeasuresSonarDTO> getSonarClientMeasures(ComponentModel component,
			List<MetricModel> sonarMetrics) {
		Optional<UriModel> sonarUri = getUri(component.getUris(), UriType.SONAR);

		if (sonarUri.isPresent()) {
			String metrics = sonarMetrics.stream().map(MetricModel::getName).collect(Collectors.joining(","));

			Response response = sonarClient.measures(URI.create(sonarUri.get().getUri()), component.getName(), metrics);

			return Optional.of(UrlUtils.mapResponse(response, MeasuresSonarDTO.class));
		}

		return Optional.empty();
	}

}
