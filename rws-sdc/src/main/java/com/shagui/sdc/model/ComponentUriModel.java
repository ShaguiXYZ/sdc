package com.shagui.sdc.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.shagui.sdc.model.pk.ComponentUriPk;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "component_uris", uniqueConstraints = { @UniqueConstraint(columnNames = { "component_id", "uri_name" }) })
public class ComponentUriModel {
	@EmbeddedId
	private ComponentUriPk id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "component_id", insertable = false, updatable = false)
	private ComponentModel component;
}
