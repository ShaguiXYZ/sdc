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
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "components", uniqueConstraints = { @UniqueConstraint(columnNames = { "squad_id", "component_name" }) })
public class ComponentModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "component_id")
	private Integer id;

	@Column(name = "component_name", nullable = false)
	private String name;

	@Column(name = "component_coverage")
	private Float coverage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "component_type_architecture_id", nullable = false)
	private ComponentTypeArchitectureModel componentTypeArchitecture;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "squad_id", nullable = false)
	private SquadModel squad;

	@OneToMany(mappedBy = "component", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComponentPropertyModel> properties;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "component_uris", joinColumns = @JoinColumn(name = "component_id", referencedColumnName = "component_id"), inverseJoinColumns = @JoinColumn(name = "uri_id", referencedColumnName = "uri_id"))
	private List<UriModel> uris;

	@OneToMany(mappedBy = "component", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComponentHistoricalCoverageModel> historicalCoverage;

}
