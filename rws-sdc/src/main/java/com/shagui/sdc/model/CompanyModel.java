package com.shagui.sdc.model;

import java.util.List;

import com.shagui.sdc.util.jpa.ModelInterface;

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

/**
 * Represents a company entity that contains multiple departments.
 * 
 * Companies are the top-level organizational units.
 * 
 * Relationships:
 * - One-to-Many with DepartmentModel: A company can contain multiple
 * departments.
 */
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
