package com.shagui.sdc.model;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "component_properties", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "component_id", "name" }) })
public class ComponentPropertyModel implements ModelInterface<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	private String value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "component_id")
	private ComponentModel component;

	public ComponentPropertyModel(ComponentModel component, String name, String value) {
		this.component = component;
		this.name = name;
		this.value = value;
	}
}
