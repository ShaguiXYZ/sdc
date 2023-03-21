package com.shagui.analysis.model;

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
@Table(name = "departments")
@NoArgsConstructor
public class DepartmentModel implements ModelInterface<Integer> {
	@Id
	@Column(name = "department_id")
	private Integer id;	
	@Column(name = "department_name")
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
