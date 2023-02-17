package com.shagui.analysis.util;

import java.util.function.Function;

import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.enums.MetricState;

public class MetricValidations {
	private MetricValidations() {

	}

	public static MetricState validateState(MetricAnalysisDTO source) {
		if (source.getMetric().getValueType() != null) {
			return source.getMetric().getValueType().validate(source);
		} else {
			return null;
		}
	}

	public static Function<MetricAnalysisDTO, MetricState> validateNumeric = (MetricAnalysisDTO source) -> {
		Float value = castNumeric(source.getAnalysisValues().getMetricValue());
		Float expectedValue = castNumeric(source.getAnalysisValues().getExpectedValue());
		Float goodValue = castNumeric(source.getAnalysisValues().getGoodValue());
		Float perfectValue = castNumeric(source.getAnalysisValues().getPerfectValue());

		if (value != null && (expectedValue != null || goodValue != null || perfectValue != null)) {
			switch (source.getMetric().getValidation()) {
			case EQUAL:
				return perfectValue != null && perfectValue == value ? MetricState.PERFECT
						: goodValue != null && goodValue == value ? MetricState.ACCEPTABLE
								: expectedValue != null && expectedValue == value ? MetricState.WITH_RISK
										: MetricState.CRITICAL;
			case MAYOR:
				return perfectValue != null && perfectValue > value ? MetricState.PERFECT
						: goodValue != null && goodValue > value ? MetricState.ACCEPTABLE
								: expectedValue != null && expectedValue > value ? MetricState.WITH_RISK
										: MetricState.CRITICAL;
			case MINOR:
				return perfectValue != null && perfectValue < value ? MetricState.PERFECT
						: goodValue != null && goodValue < value ? MetricState.ACCEPTABLE
								: expectedValue != null && expectedValue < value ? MetricState.WITH_RISK
										: MetricState.CRITICAL;
			case MAYORorEQUAL:
				return perfectValue != null && perfectValue >= value ? MetricState.PERFECT
						: goodValue != null && goodValue >= value ? MetricState.ACCEPTABLE
								: expectedValue != null && expectedValue >= value ? MetricState.WITH_RISK
										: MetricState.CRITICAL;
			case MINORorEQUAL:
				return perfectValue != null && perfectValue <= value ? MetricState.PERFECT
						: goodValue != null && goodValue <= value ? MetricState.ACCEPTABLE
								: expectedValue != null && expectedValue <= value ? MetricState.WITH_RISK
										: MetricState.CRITICAL;
			default:
				return null;
			}
		}

		return null;
	};

	// TODO Version validation
	public static Function<MetricAnalysisDTO, MetricState> validateVersion = (MetricAnalysisDTO source) -> {
		return null;
	};

	private static Float castNumeric(String toCast) {
		try {
			return toCast == null ? null : Float.valueOf(toCast);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
