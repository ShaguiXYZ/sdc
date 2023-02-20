package com.shagui.analysis.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
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
import lombok.Data;

@Data
@Entity
@Table(name = "metric_values")
public class MetricValuesModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "metric_value_id")
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

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "component_type_architecture_id")
	private ComponentTypeArchitectureModel componentTypeArchitecture;
}
