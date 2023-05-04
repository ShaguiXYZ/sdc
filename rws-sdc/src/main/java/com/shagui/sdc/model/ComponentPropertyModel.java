package com.shagui.sdc.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "component_properties", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "component_id", "component_property_name" }) })
public class ComponentPropertyModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "component_property_id")
	private Integer id;

	@Column(name = "component_property_name")
	private String name;

	@Column(name = "component_property_value")
	private String value;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "component_id")
	private ComponentModel component;

	public ComponentPropertyModel(ComponentModel component, String name, String value) {
		this.component = component;
		this.name = name;
		this.value = value;
	}
}
