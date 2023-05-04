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

import com.shagui.sdc.enums.MetricType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;

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
