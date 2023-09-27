package com.shagui.sdc.util.validations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.util.StringUtils;

import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.enums.MetricState;
import com.shagui.sdc.enums.MetricValidation;

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
			control.add(new MetricControl<>(perfectValue, MetricState.PERFECT.coverage()));
		}

		if (Objects.nonNull(goodValue)) {
			control.add(new MetricControl<>(goodValue, MetricState.ACCEPTABLE.coverage()));
		}

		if (Objects.nonNull(expectedValue)) {
			control.add(new MetricControl<>(expectedValue, MetricState.WITH_RISK.coverage()));
		}

		return control;

	}

	private static <T extends Comparable<T>> Float validate(MetricAnalysisDTO analysis, Class<T> clazz) {
		Float coverage = null;
		T value = cast(analysis.getAnalysisValues().getMetricValue(), clazz);
		MetricValidation validation = (value == null || analysis.getMetric().getValidation() == null)
				? MetricValidation.NA
				: analysis.getMetric().getValidation();
		List<MetricControl<T>> control = controlValues(analysis, clazz);

		switch (validation) {
		case EQ:
			coverage = control.stream().filter(c -> c.getControl().compareTo(value) == 0)
					.map(MetricControl::getCoverage).findFirst().orElse(MetricState.CRITICAL.coverage());
			break;
		case NEQ:
			coverage = control.stream().filter(c -> c.getControl().compareTo(value) != 0)
					.map(MetricControl::getCoverage).findFirst().orElse(MetricState.CRITICAL.coverage());
			break;
		case GT:
			coverage = control.stream().filter(c -> c.getControl().compareTo(value) > 0).map(MetricControl::getCoverage)
					.findFirst().orElse(MetricState.CRITICAL.coverage());
			break;
		case LT:
			coverage = control.stream().filter(c -> c.getControl().compareTo(value) < 0).map(MetricControl::getCoverage)
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
