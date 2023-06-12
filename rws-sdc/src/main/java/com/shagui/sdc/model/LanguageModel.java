package com.shagui.sdc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "languages", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class LanguageModel {
	@Id
	private int id;

	@Column(nullable = false)
	private String name;
}