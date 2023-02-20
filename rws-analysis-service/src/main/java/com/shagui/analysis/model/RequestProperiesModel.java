package com.shagui.analysis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "request_properies")
public class RequestProperiesModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "request_property_id")
	private Integer id;
	@Column(name = "request_property_key")
	private String key;
	@Column(name = "request_property_value")
	private String value;
}
