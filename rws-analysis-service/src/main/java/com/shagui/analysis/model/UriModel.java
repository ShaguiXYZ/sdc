package com.shagui.analysis.model;

import java.util.List;

import com.shagui.analysis.enums.UriType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
