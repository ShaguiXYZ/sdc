package com.shagui.sdc.util.validations;

import java.util.ArrayList;
import java.util.List;

import com.shagui.sdc.api.dto.AnalysisValuesDTO;
import com.shagui.sdc.enums.MetricState;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.model.ComponentAnalysisModel;

public class MetricValidations {
	private MetricValidations() {

	}

	@SuppressWarnings("unchecked")
	public static Float validate(ComponentAnalysisModel analysis) {
		if (analysis.getMetric().getValueType() != null) {
			AnalysisValuesDTO analysisValues = new AnalysisValuesDTO(analysis.getWeight(), analysis.getMetricValue(),
					analysis.getExpectedValue(), analysis.getGoodValue(), analysis.getPerfectValue());

			return MetricValidations.validate(analysisValues, analysis.getMetric().getValidation(),
					analysis.getMetric().getValueType().clazz());
		}

		return null;
	}

	private static <T extends Comparable<T>> Float validate(AnalysisValuesDTO values, MetricValidation validation,
			Class<T> clazz) {
		Float coverage = null;
		T value = ValidationsUtils.cast(values.getMetricValue(), clazz).orElse(null);
		MetricValidation metricValidation = (value == null || validation == null) ? MetricValidation.NA : validation;
		List<MetricControl<T>> control = controlValues(values, clazz);

		switch (metricValidation) {
			case EQ:
				coverage = control.stream().filter(c -> c.getControl().compareTo(value) == 0)
						.map(MetricControl::getCoverage).findFirst().orElse(MetricState.CRITICAL.coverage());
				break;
			case NEQ:
				coverage = control.stream().filter(c -> c.getControl().compareTo(value) != 0)
						.map(MetricControl::getCoverage).findFirst().orElse(MetricState.CRITICAL.coverage());
				break;
			case GT:
				coverage = control.stream().filter(c -> c.getControl().compareTo(value) > 0)
						.map(MetricControl::getCoverage)
						.findFirst().orElse(MetricState.CRITICAL.coverage());
				break;
			case LT:
				coverage = control.stream().filter(c -> c.getControl().compareTo(value) < 0)
						.map(MetricControl::getCoverage)
						.findFirst().orElse(MetricState.CRITICAL.coverage());
				break;
			case GTE:
				coverage = control.stream().filter(c -> c.getControl().compareTo(value) >= 0)
						.map(MetricControl::getCoverage).findFirst().orElse(MetricState.CRITICAL.coverage());
				break;
			case LTE:
				coverage = control.stream().filter(c -> c.getControl().compareTo(value) <= 0)
						.map(MetricControl::getCoverage).findFirst().orElse(MetricState.CRITICAL.coverage());
				break;
			default:
				return null;
		}

		return coverage;
	}

	private static <T extends Comparable<T>> List<MetricControl<T>> controlValues(AnalysisValuesDTO analysis,
			Class<T> clazz) {
		List<MetricControl<T>> control = new ArrayList<>();

		ValidationsUtils.cast(analysis.getPerfectValue(), clazz)
				.ifPresent(value -> control.add(new MetricControl<>(value, MetricState.PERFECT.coverage())));
		ValidationsUtils.cast(analysis.getGoodValue(), clazz)
				.ifPresent(value -> control.add(new MetricControl<>(value, MetricState.ACCEPTABLE.coverage())));
		ValidationsUtils.cast(analysis.getExpectedValue(), clazz)
				.ifPresent(value -> control.add(new MetricControl<>(value, MetricState.WITH_RISK.coverage())));

		return control;
	}
}
