package com.shagui.sdc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;

import lombok.Getter;
import lombok.Setter;

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
}
