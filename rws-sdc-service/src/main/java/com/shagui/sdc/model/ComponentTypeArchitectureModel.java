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
