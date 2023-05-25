package com.shagui.sdc.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.shagui.sdc.enums.AnalysisType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "uris", uniqueConstraints = { @UniqueConstraint(columnNames = { "uri_name" }) })
public class UriModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uri_id")
	private Integer id;
	@Column(name = "uri_name")
	private String name;
	@Column(name = "uri_value")
	private String uri;

	@Column(name = "uri_type")
	@Enumerated(EnumType.STRING)
	private AnalysisType type;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "component_uris", joinColumns = @JoinColumn(name = "uri_id", referencedColumnName = "uri_id"), inverseJoinColumns = @JoinColumn(name = "component_id", referencedColumnName = "component_id"))
	private List<ComponentModel> components;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "uri_request_properties", joinColumns = @JoinColumn(name = "uri_id", referencedColumnName = "uri_id"), inverseJoinColumns = @JoinColumn(name = "request_property_id", referencedColumnName = "request_property_id"))
	private List<RequestPropertiesModel> properties;

}
