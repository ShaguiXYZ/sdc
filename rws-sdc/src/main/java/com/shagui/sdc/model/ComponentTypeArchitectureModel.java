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
import javax.persistence.ManyToOne;
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
@Table(name = "component_type_architectures", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }),
		@UniqueConstraint(columnNames = { "component_type_id", "architecture_id", "network_id", "deployment_type_id",
				"platform_id", "language_id" }) })
public class ComponentTypeArchitectureModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "component_type_id")
	private ComponentTypeModel componentType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "architecture_id")
	private ArchitectureModel architecture;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "network_id")
	private NetworkModel network;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deployment_type_id")
	private DeploymentTypeModel deploymentType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "platform_id")
	private PlatformModel platform;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_id")
	private LanguageModel language;

	@OneToMany(mappedBy = "componentTypeArchitecture", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComponentModel> components;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "component_type_architecture_metrics", joinColumns = @JoinColumn(name = "component_type_architecture_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "metric_id", referencedColumnName = "id"))
	private List<MetricModel> metrics;

	public ComponentTypeArchitectureModel(ComponentTypeModel componentType, ArchitectureModel architecture) {
		this(null, componentType, architecture);
	}

	public ComponentTypeArchitectureModel(Integer id, ComponentTypeModel componentType,
			ArchitectureModel architecture) {
		this.id = id;
		this.componentType = componentType;
		this.architecture = architecture;
	}
}
