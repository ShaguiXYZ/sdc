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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "departments")
@NoArgsConstructor
public class DepartmentModel implements ModelInterface<Integer> {
	@Id
	private Integer id;

	@Column(nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private CompanyModel company;

	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<SquadModel> squads;

	public DepartmentModel(Integer id) {
		this.id = id;
	}
}
