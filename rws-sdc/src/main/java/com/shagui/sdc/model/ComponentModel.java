package com.shagui.sdc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

import com.shagui.sdc.util.jpa.JpaExpirableData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "components", uniqueConstraints = { @UniqueConstraint(columnNames = { "squad_id", "name" }) })
public class ComponentModel implements ModelInterface<Integer>, JpaExpirableData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	private Float coverage;

	@Column(name = "delete_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;

	@Column(name = "delete_user", length = 50)
	private String deleteUser;

	@Column(name = "analysis_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date analysisDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "component_type_architecture_id", nullable = false)
	private ComponentTypeArchitectureModel componentTypeArchitecture;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "squad_id", nullable = false)
	private SquadModel squad;

	@OneToMany(mappedBy = "component", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComponentPropertyModel> properties = new ArrayList<>();

	@OneToMany(mappedBy = "component", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComponentUriModel> uris = new ArrayList<>();

	@OneToMany(mappedBy = "component", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComponentHistoricalCoverageModel> historicalCoverage = new ArrayList<>();

}
