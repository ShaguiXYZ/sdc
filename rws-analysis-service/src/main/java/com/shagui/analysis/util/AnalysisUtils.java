package com.shagui.analysis.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.shagui.analysis.api.dto.ComponentStateDTO;
import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.model.ComponentAnalysisModel;
import com.shagui.analysis.model.MetricValuesModel;

public class AnalysisUtils {
	private static AnalysisUtilsConfig config;

	private AnalysisUtils() {
	}

	public static void setConfig(AnalysisUtilsConfig config) {
		AnalysisUtils.config = config;
	}

	public static Function<ComponentAnalysisModel, MetricAnalysisDTO> transformToMetricAnalysis = (analysis) -> {
		List<MetricValuesModel> metricValues = config.metricValuesRepository().repository().metricValueByDate(
				analysis.getMetric().getId(), analysis.getComponentTypeArchitecture().getId(),
				analysis.getId().getComponentAnalysisDate());

		if (!metricValues.isEmpty()) {
			MetricValuesModel value = metricValues.get(0);
			analysis.setWeight(value.getWeight());
			analysis.setExpectedValue(value.getValue());
			analysis.setGoodValue(value.getGoodValue());
			analysis.setPerfectValue(value.getPerfectValue());
		}

		return Mapper.parse(analysis);
	};

	public static ComponentStateDTO calculateComponentState(List<ComponentAnalysisModel> metricAnalysis) {
		List<MetricAnalysisDTO> dtos = metricAnalysis.stream().map(transformToMetricAnalysis)
				.collect(Collectors.toList());

		ComponentStateDTO state = new ComponentStateDTO();
		state.setMetricAnalysis(dtos);
		state.setCoverage(calculateCoverage(dtos));

		return state;
	}

	private static float calculateCoverage(List<MetricAnalysisDTO> metricAnalysis) {
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
			float relativeWeight = (float) metricAnalysis.getAnalysisValues().getWeight() / totalWeight;

			return metricAnalysis.getCoverage() * relativeWeight;
		};
	}
}
