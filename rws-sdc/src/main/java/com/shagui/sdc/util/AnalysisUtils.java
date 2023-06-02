package com.shagui.sdc.util;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.MetricValuesModel;

public class AnalysisUtils {
	private static AnalysisUtilsConfig config;

	private AnalysisUtils() {
	}

	public static void setConfig(AnalysisUtilsConfig config) {
		AnalysisUtils.config = config;
	}

	public static final UnaryOperator<ComponentAnalysisModel> setMetricValues = analysis -> {
		Optional<MetricValuesModel> metricValues = config.metricValuesRepository().repository()
				.metricValueByDate(analysis.getMetric().getId(), analysis.getComponentTypeArchitecture().getId(),
						new Timestamp(analysis.getId().getAnalysisDate().getTime()))
				.stream().findFirst();

		ComponentAnalysisModel updatedModel = new ComponentAnalysisModel(analysis.getComponent(), analysis.getMetric(),
				analysis.getValue(), analysis.getId().getAnalysisDate());

		if (metricValues.isPresent()) {
			MetricValuesModel value = metricValues.get();
			updatedModel.setWeight(value.getWeight());
			updatedModel.setExpectedValue(value.getValue());
			updatedModel.setGoodValue(value.getGoodValue());
			updatedModel.setPerfectValue(value.getPerfectValue());
		}

		return updatedModel;
	};

	public static Float metricCoverage(List<ComponentAnalysisModel> metricAnalysis) {
		List<MetricAnalysisDTO> dtos = metricAnalysis.stream().map(setMetricValues).map(Mapper::parse)
				.collect(Collectors.toList());

		return calculateMetricCoverage(dtos);
	}

	private static float calculateMetricCoverage(List<MetricAnalysisDTO> metricAnalysis) {
		List<MetricAnalysisDTO> analysisWithCoverage = metricAnalysis.stream()
				.filter(data -> data.getCoverage() != null).collect(Collectors.toList());
		int totalWeight = analysisWithCoverage.stream().map(data -> data.getAnalysisValues().getWeight()).reduce(0,
				(a, b) -> a + b);
		return analysisWithCoverage.stream().map(metricAnalysisRelativeCoverage(totalWeight)).reduce(0f,
				(a, b) -> a + b);
	}

	private static Function<MetricAnalysisDTO, Float> metricAnalysisRelativeCoverage(int totalWeight) {
		return (MetricAnalysisDTO metricAnalysis) -> {
			float relativeWeight = totalWeight == 0 ? 0
					: (float) metricAnalysis.getAnalysisValues().getWeight() / totalWeight;

			return metricAnalysis.getCoverage() * relativeWeight;
		};
	}
}
