package com.shagui.analysis.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "companies")
public class CompanyModel {
	@Id
	@Column(name = "company_id")
	private int id;	
	@Column(name = "company_name")
	private String name;
	@Column(name = "company_codes")
	private String codes;
	
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<DepartmentModel> departments;

}
