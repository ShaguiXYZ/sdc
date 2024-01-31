package com.shagui.sdc.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "companies")
public class CompanyModel implements ModelInterface<Integer> {
	@Id
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String codes;

	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("name ASC")
	private List<DepartmentModel> departments;

	public CompanyModel(int id) {
		this.id = id;
	}
}
