package com.shagui.analysis.model;

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

import com.shagui.analysis.enums.UriType;

import lombok.Data;

@Data
@Entity
@Table(name = "uris")
public class UriModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uri_id")
	private int id;
	@Column(name = "uri_name")
	private String name;
	@Column(name = "uri_value")
	private String uri;

	@Column(name = "uri_type")
	@Enumerated(EnumType.STRING)
	private UriType type;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "component_uris", joinColumns = @JoinColumn(name = "uri_id", referencedColumnName = "uri_id"), inverseJoinColumns = @JoinColumn(name = "component_id", referencedColumnName = "component_id"))
	private List<ComponentModel> components;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "uri_request_properties", joinColumns = @JoinColumn(name = "uri_id", referencedColumnName = "uri_id"), inverseJoinColumns = @JoinColumn(name = "request_property_id", referencedColumnName = "request_property_id"))
	private List<RequestProperiesModel> properties;

}