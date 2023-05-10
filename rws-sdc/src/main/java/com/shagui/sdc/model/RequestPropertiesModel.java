package com.shagui.sdc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "request_properties")
public class RequestPropertiesModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "request_property_id")
	private Integer id;
	@Column(name = "request_property_key")
	private String key;
	@Column(name = "request_property_value")
	private String value;
}
