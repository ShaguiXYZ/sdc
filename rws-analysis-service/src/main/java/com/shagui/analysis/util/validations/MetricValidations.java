package com.shagui.analysis.util.validations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.util.StringUtils;

import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.enums.MetricState;

public class MetricValidations {
	private MetricValidations() {

	}

	@SuppressWarnings("unchecked")
	public static Float validate(MetricAnalysisDTO analysis) {
		if (analysis.getMetric().getValueType() != null) {
			return MetricValidations.validate(analysis, analysis.getMetric().getValueType().clazz());
		}

		return null;
	}

	private static <T extends Comparable<T>> List<MetricControl<T>> controlValues(MetricAnalysisDTO analysis,
			Class<T> clazz) {

		T perfectValue = cast(analysis.getAnalysisValues().getPerfectValue(), clazz);
		T goodValue = cast(analysis.getAnalysisValues().getGoodValue(), clazz);
		T expectedValue = cast(analysis.getAnalysisValues().getExpectedValue(), clazz);

		List<MetricControl<T>> control = new ArrayList<>();

		if (Objects.nonNull(perfectValue)) {
			control.add(new MetricControl<T>(perfectValue, MetricState.PERFECT.coverage()));
		}

		if (Objects.nonNull(goodValue)) {
			control.add(new MetricControl<T>(goodValue, MetricState.ACCEPTABLE.coverage()));
		}

		if (Objects.nonNull(expectedValue)) {
			control.add(new MetricControl<T>(expectedValue, MetricState.WITH_RISK.coverage()));
		}

		return control;

	}

	private static <T extends Comparable<T>> Float validate(MetricAnalysisDTO analysis, Class<T> clazz) {
		Float coverage = null;
		T value = cast(analysis.getAnalysisValues().getMetricValue(), clazz);
		List<MetricControl<T>> control = controlValues(analysis, clazz);

		switch (analysis.getMetric().getValidation()) {
		case EQUAL:
			coverage = control.stream().filter(c -> c.getControl().compareTo(value) == 0)
					.map(data -> data.getCoverage()).findFirst().orElse(MetricState.CRITICAL.coverage());
			break;
		case MAYOR:
			coverage = control.stream().filter(c -> c.getControl().compareTo(value) == 1)
					.map(data -> data.getCoverage()).findFirst().orElse(MetricState.CRITICAL.coverage());
			break;
		case MINOR:
			coverage = control.stream().filter(c -> c.getControl().compareTo(value) == -1)
					.map(data -> data.getCoverage()).findFirst().orElse(MetricState.CRITICAL.coverage());
			break;
		case MAYORorEQUAL:
			coverage = control.stream().filter(c -> c.getControl().compareTo(value) >= 0)
					.map(data -> data.getCoverage()).findFirst().orElse(MetricState.CRITICAL.coverage());
			break;
		case MINORorEQUAL:
			coverage = control.stream().filter(c -> c.getControl().compareTo(value) <= 0)
					.map(data -> data.getCoverage()).findFirst().orElse(MetricState.CRITICAL.coverage());
			break;
		default:
			return null;
		}

		return coverage;
	}

	private static <T extends Comparable<T>> T cast(String toCast, Class<T> clazz) {
		try {
			Constructor<T> constructor = clazz.getConstructor(String.class);
			return StringUtils.hasText(toCast) ? constructor.newInstance(toCast) : null;
		} catch (IllegalArgumentException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | InvocationTargetException e) {
			return null;
		}
	}
}
