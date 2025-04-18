package com.shagui.sdc.model;

import com.shagui.sdc.util.jpa.ModelInterface;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the properties of metrics associated with a specific component
 * type architecture.
 * 
 * Relationships:
 * - Many-to-One with MetricModel: Links this property to a specific metric.
 * - Many-to-One with ComponentTypeArchitectureModel: Links this property to a
 * specific component type architecture.
 */
@Getter
@Setter
@Entity
@Table(name = "component_type_architecture_metrics_properties", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "metric_id", "component_type_architecture_id", "name" }) })
public class ComponetTypeArchitectureMetricPropertiesModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "metric_id")
	private MetricModel metric;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "component_type_architecture_id")
	private ComponentTypeArchitectureModel componentTypeArchitecture;
}
