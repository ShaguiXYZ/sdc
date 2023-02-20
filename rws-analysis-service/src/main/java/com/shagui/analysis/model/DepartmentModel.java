package com.shagui.analysis.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "departments")
public class DepartmentModel {
	@Id
	@Column(name = "department_id")
	private int id;	
	@Column(name = "department_name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private CompanyModel company;
	
	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<SquadModel> squads;
}
