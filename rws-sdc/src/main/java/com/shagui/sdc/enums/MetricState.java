package com.shagui.sdc.enums;

/**
 * Enum representing the state of a metric based on its coverage value.
 * Each state is associated with a specific coverage threshold.
 */
public enum MetricState {

	/**
	 * Represents a critical state with the lowest coverage threshold.
	 * Coverage: 10%
	 */
	CRITICAL(10f),

	/**
	 * Represents a state with some risk, having a moderate coverage threshold.
	 * Coverage: 50%
	 */
	WITH_RISK(50f),

	/**
	 * Represents an acceptable state with a higher coverage threshold.
	 * Coverage: 75%
	 */
	ACCEPTABLE(75f),

	/**
	 * Represents a perfect state with the highest coverage threshold.
	 * Coverage: 100%
	 */
	PERFECT(100f);

	private Float coverage;

	/**
	 * Constructor to associate a coverage value with the metric state.
	 *
	 * @param coverage the coverage value associated with the metric state
	 */
	MetricState(Float coverage) {
		this.coverage = coverage;
	}

	/**
	 * Retrieves the coverage value associated with the metric state.
	 *
	 * @return the coverage value
	 */
	public Float coverage() {
		return coverage;
	}
}
