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

import com.shagui.sdc.util.jpa.ModelInterface;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the MetricValuesModel entity which maps to the "metric_values"
 * table in the database.
 * This entity stores information about metric values, including their
 * associated metrics and
 * component type architectures.
 * 
 * <p>
 * Fields:
 * </p>
 * <ul>
 * <li><b>id</b>: The unique identifier for the metric value. It is
 * auto-generated.</li>
 * <li><b>metricValueDate</b>: The timestamp when the metric value was created.
 * It is automatically
 * populated and cannot be updated.</li>
 * <li><b>weight</b>: The weight of the metric value. Defaults to 0 if not
 * specified.</li>
 * <li><b>value</b>: The expected value of the metric. This field is
 * mandatory.</li>
 * <li><b>goodValue</b>: The value considered "good" for the metric. This field
 * is optional.</li>
 * <li><b>perfectValue</b>: The value considered "perfect" for the metric. This
 * field is optional.</li>
 * <li><b>metric</b>: The associated metric for this value. This is a mandatory
 * many-to-one
 * relationship with the MetricModel entity.</li>
 * <li><b>componentTypeArchitecture</b>: The associated component type
 * architecture for this value.
 * This is a mandatory many-to-one relationship with the
 * ComponentTypeArchitectureModel entity.</li>
 * </ul>
 * 
 * <p>
 * Annotations:
 * </p>
 * <ul>
 * <li><b>@Entity</b>: Specifies that this class is an entity and is mapped to a
 * database table.</li>
 * <li><b>@Table(name = "metric_values")</b>: Maps this entity to the
 * "metric_values" table.</li>
 * <li><b>@Id</b>: Marks the primary key of the entity.</li>
 * <li><b>@GeneratedValue(strategy = GenerationType.IDENTITY)</b>: Specifies
 * that the primary key
 * is auto-generated using the identity strategy.</li>
 * <li><b>@CreationTimestamp</b>: Automatically populates the metricValueDate
 * field with the
 * current timestamp when the entity is created.</li>
 * <li><b>@Temporal(TemporalType.TIMESTAMP)</b>: Specifies that the
 * metricValueDate field is of
 * type TIMESTAMP.</li>
 * <li><b>@Column</b>: Maps fields to specific columns in the database table
 * with additional
 * constraints and definitions.</li>
 * <li><b>@ManyToOne</b>: Defines a many-to-one relationship with another
 * entity.</li>
 * <li><b>@JoinColumn</b>: Specifies the foreign key column for the
 * relationship.</li>
 * <li><b>@Getter</b> and <b>@Setter</b>: Lombok annotations to automatically
 * generate getter and
 * setter methods for all fields.</li>
 * </ul>
 * 
 * <p>
 * Implements:
 * </p>
 * <ul>
 * <li><b>ModelInterface&lt;Integer&gt;</b>: A generic interface that this model
 * implements,
 * parameterized with Integer as the type of the primary key.</li>
 * </ul>
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
	@Column(name = "metric_value_date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date metricValueDate;

	@Column(name = "metric_value_weight", nullable = false, columnDefinition = "int4 DEFAULT 0")
	private int weight;

	@Column(name = "metric_expected_value", nullable = false)
	private String value;

	@Column(name = "metric_good_value", nullable = true)
	private String goodValue;

	@Column(name = "metric_perfect_value", nullable = true)
	private String perfectValue;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "metric_id", nullable = false)
	private MetricModel metric;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "component_type_architecture_id", nullable = false)
	private ComponentTypeArchitectureModel componentTypeArchitecture;
}
