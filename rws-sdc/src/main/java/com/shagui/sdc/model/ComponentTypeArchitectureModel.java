package com.shagui.sdc.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	@OneToMany(mappedBy = "componentTypeArchitecture", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComponentModel> components;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "component_type_architecture_metrics", joinColumns = @JoinColumn(name = "component_type_architecture_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "metric_id", referencedColumnName = "id"))
	private List<MetricModel> metrics;
}
