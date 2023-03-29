package com.shagui.sdc.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "squads")
@NoArgsConstructor
public class SquadModel implements ModelInterface<Integer> {
	@Id
	@Column(name = "squad_id")
	private Integer id;

	@Column(name = "squad_name", nullable = false)
	private String name;

	@Column(name = "squad_coverage")
	private Float coverage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private DepartmentModel department;

	@OneToMany(mappedBy = "squad", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<ComponentModel> components;

	public SquadModel(Integer id) {
		this.id = id;
	}
}
