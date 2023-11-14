package com.shagui.sdc.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.shagui.sdc.model.pk.ComponentUriPk;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "component_uris", uniqueConstraints = { @UniqueConstraint(columnNames = { "component_id", "uri_name" }) })
public class ComponentUriModel implements ModelInterface<ComponentUriPk> {
	@EmbeddedId
	private ComponentUriPk id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("componentId")
	@JoinColumn(name = "component_id", insertable = false, updatable = false)
	private ComponentModel component;
}
