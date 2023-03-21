package com.shagui.sdc.util;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.MetricAnalysisStateDTO;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.MetricValuesModel;

public class AnalysisUtils {
	private static AnalysisUtilsConfig config;

	private AnalysisUtils() {
	}

	public static void setConfig(AnalysisUtilsConfig config) {
		AnalysisUtils.config = config;
	}

	public static UnaryOperator<ComponentAnalysisModel> setMetricValues = (analysis) -> {
		List<MetricValuesModel> metricValues = config.metricValuesRepository().repository().metricValueByDate(
				analysis.getMetric().getId(), analysis.getComponentTypeArchitecture().getId(),
				analysis.getId().getComponentAnalysisDate());

		ComponentAnalysisModel updatedModel = new ComponentAnalysisModel(analysis.getComponent(), analysis.getMetric(),
				analysis.getValue(), analysis.getId().getComponentAnalysisDate());

		if (!metricValues.isEmpty()) {
			MetricValuesModel value = metricValues.get(0);
			updatedModel.setWeight(value.getWeight());
			updatedModel.setExpectedValue(value.getValue());
			updatedModel.setGoodValue(value.getGoodValue());
			updatedModel.setPerfectValue(value.getPerfectValue());
		}

		return updatedModel;
	};

	public static MetricAnalysisStateDTO metricCoverage(List<ComponentAnalysisModel> metricAnalysis) {
		List<MetricAnalysisDTO> dtos = metricAnalysis.stream().map(setMetricValues).map(Mapper::parse)
				.collect(Collectors.toList());

		MetricAnalysisStateDTO state = new MetricAnalysisStateDTO();
		state.setMetricAnalysis(dtos);
		state.setCoverage(calculateMetricCoverage(dtos));

		return state;
	}

	private static float calculateMetricCoverage(List<MetricAnalysisDTO> metricAnalysis) {
		List<MetricAnalysisDTO> analysisWithCoverage = metricAnalysis.stream()
				.filter(data -> data.getCoverage() != null).collect(Collectors.toList());
		int totalWeight = analysisWithCoverage.stream().map(data -> data.getAnalysisValues().getWeight()).reduce(0,
				(a, b) -> a + b);
		float sumOfAllMetricCoverages = analysisWithCoverage.stream().map(metricAnalysisRelativeCoverage(totalWeight))
				.reduce(0f, (a, b) -> a + b);

		return sumOfAllMetricCoverages;

//		return ThreadLocalRandom.current().nextInt(0, 100);
	}

	private static Function<MetricAnalysisDTO, Float> metricAnalysisRelativeCoverage(int totalWeight) {
		return (MetricAnalysisDTO metricAnalysis) -> {
			float relativeWeight = totalWeight == 0 ? 0
					: (float) metricAnalysis.getAnalysisValues().getWeight() / totalWeight;

			return metricAnalysis.getCoverage() * relativeWeight;
		};
	}
}
