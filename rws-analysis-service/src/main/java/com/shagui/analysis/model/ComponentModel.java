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

@Data
@Entity
@Table(name = "components", uniqueConstraints = { @UniqueConstraint(columnNames = { "squad_id", "component_name" }) })
public class ComponentModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "component_id")
	private Integer id;

	@Column(name = "component_name", nullable = false)
	private String name;
	
	@Column(name = "component_path")
	private String path;

	@Column(name = "component_private")
	private boolean nonPublic;

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

}
