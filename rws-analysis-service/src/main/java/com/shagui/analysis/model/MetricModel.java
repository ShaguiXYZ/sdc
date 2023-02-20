package com.shagui.analysis.model;

import com.shagui.analysis.enums.MetricType;
import com.shagui.analysis.enums.MetricValidation;
import com.shagui.analysis.enums.MetricValueType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "metrics", uniqueConstraints = { @UniqueConstraint(columnNames = { "metric_name", "metric_type" }) })
public class MetricModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "metric_id")
	private Integer id;

	@Column(name = "metric_name")
	private String name;

	@Column(name = "metric_type")
	@Enumerated(EnumType.STRING)
	private MetricType type;

	@Column(name = "metric_value_type")
	@Enumerated(EnumType.STRING)
	private MetricValueType valueType;

	@Column(name = "metric_validation")
	@Enumerated(EnumType.STRING)
	private MetricValidation validation;
}
