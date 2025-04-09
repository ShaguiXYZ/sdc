package com.shagui.sdc.model;

import java.util.List;

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
 * Represents the architecture of a specific component type, including its
 * deployment and platform details.
 * 
 * Relationships:
 * - One-to-Many with ComponentModel: Links this architecture to multiple
 * components.
 * - Many-to-Many with MetricModel: Links this architecture to multiple metrics.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "component_type_architectures", uniqueConstraints = { @UniqueConstraint(columnNames = { "component_type",
		"architecture", "network", "deployment_type", "platform", "language" }) })
public class ComponentTypeArchitectureModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "component_type", nullable = false)
	private String componentType;

	@Column(nullable = false)
	private String architecture;

	@Column(nullable = false)
	private String network;

	@Column(name = "deployment_type", nullable = false)
	private String deploymentType;

	@Column(nullable = false)
	private String platform;

	@Column(nullable = false)
	private String language;

	@OneToMany(mappedBy = "componentTypeArchitecture", fetch = FetchType.LAZY)
	private List<ComponentModel> components;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "component_type_architecture_metrics", joinColumns = @JoinColumn(name = "component_type_architecture_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "metric_id", referencedColumnName = "id"))
	private List<MetricModel> metrics;
}
