package com.shagui.sdc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a metric entity with attributes such as name, value, type, and
 * validation.
 * 
 * Metrics can be associated with component type architectures.
 * 
 * Relationships:
 * - None explicitly defined in this class, but metrics can be linked to
 * component type architectures.
 */
@Getter
@Setter
@Entity
@Table(name = "metrics", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "type" }) })
public class MetricModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String value;

	private String description;

	@Enumerated(EnumType.STRING)
	private AnalysisType type;

	@Column(name = "metric_value_type")
	@Enumerated(EnumType.STRING)
	private MetricValueType valueType;

	@Column(name = "metric_validation")
	@Enumerated(EnumType.STRING)
	private MetricValidation validation;

	private boolean blocker;
}
