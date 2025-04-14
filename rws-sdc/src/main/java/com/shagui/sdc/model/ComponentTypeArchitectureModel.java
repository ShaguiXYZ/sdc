package com.shagui.sdc.model;

import java.util.List;

import com.shagui.sdc.util.jpa.ModelInterface;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a model for the association between a component type and its
 * architecture.
 * This entity is mapped to the "component_type_architectures" table in the
 * database
 * and includes unique constraints on several fields to ensure data integrity.
 * 
 * <p>
 * The model defines the relationship between component types, architectures,
 * networks,
 * deployment types, platforms, and programming languages. It also establishes
 * relationships with other entities such as {@link ComponentModel} and
 * {@link MetricModel}.
 * </p>
 * 
 * <p>
 * This class is annotated with JPA and Lombok annotations to simplify
 * persistence
 * and boilerplate code generation.
 * </p>
 * 
 * <p>
 * Relationships:
 * <ul>
 * <li>One-to-Many with {@link ComponentModel} (components)</li>
 * <li>Many-to-Many with {@link MetricModel} (metrics)</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Table Details:
 * <ul>
 * <li>Table Name: component_type_architectures</li>
 * <li>Unique Constraints: component_type, architecture, network,
 * deployment_type, platform, language</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Fields:
 * <ul>
 * <li>id: Primary key, auto-generated</li>
 * <li>componentType: The type of the component (non-null, max length 255)</li>
 * <li>architecture: The architecture of the component (non-null, max length
 * 255)</li>
 * <li>network: The network associated with the component (non-null, max length
 * 255)</li>
 * <li>deploymentType: The deployment type of the component (non-null, max
 * length 255)</li>
 * <li>platform: The platform on which the component runs (non-null, max length
 * 255)</li>
 * <li>language: The programming language used by the component (non-null, max
 * length 255)</li>
 * <li>components: List of associated {@link ComponentModel} entities</li>
 * <li>metrics: List of associated {@link MetricModel} entities</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Annotations:
 * <ul>
 * <li>{@code @Entity}: Marks this class as a JPA entity</li>
 * <li>{@code @Table}: Specifies the table name and unique constraints</li>
 * <li>{@code @Id}: Marks the primary key field</li>
 * <li>{@code @GeneratedValue}: Specifies the generation strategy for the
 * primary key</li>
 * <li>{@code @Column}: Configures column properties for fields</li>
 * <li>{@code @OneToMany}: Defines a one-to-many relationship with
 * {@link ComponentModel}</li>
 * <li>{@code @ManyToMany}: Defines a many-to-many relationship with
 * {@link MetricModel}</li>
 * <li>{@code @JoinTable}: Configures the join table for the many-to-many
 * relationship</li>
 * <li>{@code @Getter}, {@code @Setter}: Lombok annotations to generate getters
 * and setters</li>
 * <li>{@code @NoArgsConstructor}: Lombok annotation to generate a no-argument
 * constructor</li>
 * </ul>
 * </p>
 * 
 * @author Shagui
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "component_type_architectures", uniqueConstraints = {
		@UniqueConstraint(columnNames = {
				"component_type",
				"architecture",
				"network",
				"deployment_type",
				"platform",
				"language"
		})
})
public class ComponentTypeArchitectureModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "component_type", nullable = false, length = 255)
	private String componentType;

	@Column(nullable = false, length = 255)
	private String architecture;

	@Column(nullable = false, length = 255)
	private String network;

	@Column(name = "deployment_type", nullable = false, length = 255)
	private String deploymentType;

	@Column(nullable = false, length = 255)
	private String platform;

	@Column(nullable = false, length = 255)
	private String language;

	@OneToMany(mappedBy = "componentTypeArchitecture", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComponentModel> components;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "component_type_architecture_metrics", joinColumns = @JoinColumn(name = "component_type_architecture_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "metric_id", referencedColumnName = "id"))
	private List<MetricModel> metrics;
}
