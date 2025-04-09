package com.shagui.sdc.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the values of a metric for a specific component type architecture.
 * 
 * Includes attributes such as weight, expected value, good value, and perfect
 * value.
 * 
 * Relationships:
 * - Many-to-One with MetricModel: Each metric value is associated with a
 * specific metric.
 * - Many-to-One with ComponentTypeArchitectureModel: Each metric value is
 * linked to a specific component type architecture.
 */
@Getter
@Setter
@Entity
@Table(name = "metric_values")
public class MetricValuesModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@CreationTimestamp
	@Column(name = "metric_value_date", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date metricValueDate;

	@Column(name = "metric_value_weight")
	private int weight;

	@Column(name = "metric_expected_value")
	private String value;

	@Column(name = "metric_good_value")
	private String goodValue;

	@Column(name = "metric_perfect_value")
	private String perfectValue;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "metric_id")
	private MetricModel metric;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "component_type_architecture_id")
	private ComponentTypeArchitectureModel componentTypeArchitecture;
}
