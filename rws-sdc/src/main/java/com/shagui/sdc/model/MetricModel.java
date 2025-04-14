package com.shagui.sdc.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;
import com.shagui.sdc.util.jpa.ModelInterface;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a metric entity with attributes such as name, value, type, and
 * validation. This entity is used to define and manage metrics that can be
 * associated with component type architectures.
 * 
 * <p>
 * The {@code MetricModel} class is annotated with JPA annotations to map it to
 * a database table named "metrics". It includes constraints to ensure the
 * uniqueness of certain fields and defines relationships with other entities.
 * </p>
 * 
 * <p>
 * Attributes:
 * </p>
 * <ul>
 * <li>{@code id} - The unique identifier for the metric (Primary Key).</li>
 * <li>{@code name} - The name of the metric (must be unique and not null).</li>
 * <li>{@code value} - The value of the metric (must not be null).</li>
 * <li>{@code description} - An optional description of the metric.</li>
 * <li>{@code type} - The type of analysis associated with the metric (enum,
 * must not be null).</li>
 * <li>{@code valueType} - The type of value the metric holds (optional
 * enum).</li>
 * <li>{@code validation} - The validation type for the metric (optional
 * enum).</li>
 * <li>{@code blocker} - A boolean flag indicating if the metric is a blocker
 * (must not be null).</li>
 * <li>{@code remoteConfig} - A one-to-one relationship with the
 * {@code RemoteMetricConfigModel} entity, representing the remote configuration
 * for the metric. This relationship is lazy-loaded, cascades all operations,
 * and supports orphan removal.</li>
 * </ul>
 * 
 * <p>
 * Relationships:
 * </p>
 * <ul>
 * <li>{@code remoteConfig} - A one-to-one relationship with the
 * {@code RemoteMetricConfigModel} entity.</li>
 * </ul>
 * 
 * <p>
 * Constraints:
 * </p>
 * <ul>
 * <li>The combination of {@code name} and {@code type} must be unique.</li>
 * </ul>
 * 
 * <p>
 * Annotations:
 * </p>
 * <ul>
 * <li>{@code @Entity} - Marks this class as a JPA entity.</li>
 * <li>{@code @Table} - Specifies the table name and unique constraints.</li>
 * <li>{@code @Id} - Marks the {@code id} field as the primary key.</li>
 * <li>{@code @GeneratedValue} - Specifies the generation strategy for the
 * primary key.</li>
 * <li>{@code @Column} - Maps fields to database columns and defines constraints
 * such as nullability and uniqueness.</li>
 * <li>{@code @Enumerated} - Specifies that certain fields are enums and should
 * be stored as strings in the database.</li>
 * <li>{@code @OneToOne} - Defines a one-to-one relationship with another
 * entity.</li>
 * </ul>
 * 
 * <p>
 * Lombok Annotations:
 * </p>
 * <ul>
 * <li>{@code @Getter} - Automatically generates getter methods for all
 * fields.</li>
 * <li>{@code @Setter} - Automatically generates setter methods for all
 * fields.</li>
 * </ul>
 */

@Getter
@Setter
@Entity
@Table(name = "metrics", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "type" }) })
public class MetricModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private String value;

	@Column(nullable = true)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AnalysisType type;

	@Column(name = "metric_value_type", nullable = true)
	@Enumerated(EnumType.STRING)
	private MetricValueType valueType;

	@Column(name = "metric_validation", nullable = true)
	@Enumerated(EnumType.STRING)
	private MetricValidation validation;

	@Column(nullable = false)
	private boolean blocker;

	@OneToOne(mappedBy = "metric", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private RemoteMetricConfigModel remoteConfig;
}
