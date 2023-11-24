package com.shagui.sdc.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.SonarClient;
import com.shagui.sdc.api.dto.sonar.MeasureSonarDTO;
import com.shagui.sdc.api.dto.sonar.MeasuresSonarDTO;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.service.SonarService;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.UrlUtils;

import feign.Response;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service(Ctes.AnalysisServicesTypes.SONAR)
public class SonarServiceImpl implements SonarService {
	private SonarClient sonarClient;

	@Override
	public List<ComponentAnalysisModel> analyze(ComponentModel component) {
		List<ComponentAnalysisModel> analysisList = new ArrayList<>();
		List<MetricModel> sonarMetrics = metrics(component);

		if (!sonarMetrics.isEmpty()) {
			Optional<MeasuresSonarDTO> measures = getSonarClientMeasures(component, sonarMetrics);

			if (measures.isPresent()) {
				sonarMetrics.forEach(metric -> {
					Optional<MeasureSonarDTO> measure = measureByMetric(measures.get(), metric);

					if (measure.isPresent()) {
						analysisList
								.add(new ComponentAnalysisModel(component, metric, measure.get().getValue()));
					}
				});
			}
		}

		return analysisList;
	}

	private Optional<MeasureSonarDTO> measureByMetric(MeasuresSonarDTO measures, MetricModel metric) {
		return measures.getMeasures().stream().filter(m -> m.getMetric().equals(metric.getValue())).findFirst();
	}

	private Optional<MeasuresSonarDTO> getSonarClientMeasures(ComponentModel component,
			List<MetricModel> sonarMetrics) {
		Optional<String> sonarUri = uri(component);

		if (sonarUri.isPresent()) {
			String metrics = sonarMetrics.stream().map(MetricModel::getValue).collect(Collectors.joining(","));
			Response response = sonarClient.measures(URI.create(sonarUri.get()), component.getName(), metrics);

			return Optional.of(UrlUtils.mapResponse(response, MeasuresSonarDTO.class));
		}

		return Optional.empty();
	}

	private Optional<String> uri(ComponentModel component) {
		String uri = null;
		Optional<UriModel> uriModel = UrlUtils.componentUri(component, UriType.SONAR);

		if (uriModel.isPresent()) {
			uri = uriModel.get().getValue();
		}

		return Optional.ofNullable(uri);
	}
}
