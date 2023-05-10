package com.shagui.sdc.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
