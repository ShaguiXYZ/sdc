package com.shagui.analysis.model;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "component_type_architectures", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "component_type_id", "architecture_id" }) })
public class ComponentTypeArchitectureModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "component_type_architecture_id")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "component_type_id")
	private ComponentTypeModel componentType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "architecture_id")
	private ArchitectureModel architecture;

	@OneToMany(mappedBy = "componentTypeArchitecture", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComponentModel> components;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "component_type_architecture_metrics", joinColumns = @JoinColumn(name = "component_type_architecture_id", referencedColumnName = "component_type_architecture_id"), inverseJoinColumns = @JoinColumn(name = "metric_id", referencedColumnName = "metric_id"))
	private List<MetricModel> metrics;
	
	public ComponentTypeArchitectureModel(ComponentTypeModel componentType, ArchitectureModel architecture) {
		this(null, componentType, architecture);
	}
	
	public ComponentTypeArchitectureModel(Integer id, ComponentTypeModel componentType, ArchitectureModel architecture) {
		this.id = id;
		this.componentType = componentType;
		this.architecture = architecture;
	}
}
