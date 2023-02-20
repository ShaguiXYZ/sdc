package com.shagui.analysis.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "component_properties", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "component_property_name", "component_id" }) })
public class ComponentPropertyModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "component_property_id")
	private int id;
	
	@Column(name = "component_property_name")
	private String name;
	
	@Column(name = "component_property_value")
	private String value;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "component_id")
	private ComponentModel component;
}
