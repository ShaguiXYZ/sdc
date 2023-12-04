package com.shagui.sdc.util;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.ServiceDataDTO;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.MetricValuesModel;
import com.shagui.sdc.util.validations.MetricValidations;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnalysisUtils {
	private static AnalysisUtilsConfig config;

	private AnalysisUtils() {
	}

	protected static void setConfig(AnalysisUtilsConfig config) {
		AnalysisUtils.config = config;
	}

	public static Supplier<String> notDataFound(ServiceDataDTO serviceData) {
		return () -> {
			log.debug("There is no analysis data for component '{0}' and metric '{1}'",
					serviceData.getComponent().getName(), serviceData.getMetric().getName());

			return "N/A";
		};
	}

	public static final UnaryOperator<ComponentAnalysisModel> setMetricValues = analysis -> {
		ComponentAnalysisModel updatedModel = new ComponentAnalysisModel(analysis.getComponent(), analysis.getMetric(),
				analysis.getMetricValue(), analysis.getId().getAnalysisDate(), analysis.isBlocker());

		Optional<MetricValuesModel> metricValues = config.metricValuesRepository().repository()
				.metricValueByDate(analysis.getMetric().getId(), analysis.getComponentTypeArchitecture().getId(),
						new Timestamp(analysis.getId().getAnalysisDate().getTime()))
				.stream().findFirst();

		if (metricValues.isPresent()) {
			MetricValuesModel value = metricValues.get();
			updatedModel.setWeight(value.getWeight());
			updatedModel.setExpectedValue(value.getValue());
			updatedModel.setGoodValue(value.getGoodValue());
			updatedModel.setPerfectValue(value.getPerfectValue());
			updatedModel.setCoverage(MetricValidations.validate(updatedModel));
		}

		return updatedModel;
	};

	public static Float metricCoverage(List<ComponentAnalysisModel> metricAnalysis) {
		List<MetricAnalysisDTO> dtos = metricAnalysis.stream().map(setMetricValues).map(Mapper::parse).toList();

		return NumericUtils.round(calculateMetricCoverage(dtos), 2);
	}

	private static float calculateMetricCoverage(List<MetricAnalysisDTO> metricAnalysis) {
		List<MetricAnalysisDTO> analysisWithCoverage = metricAnalysis.stream()
				.filter(data -> data.getCoverage() != null).toList();
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
