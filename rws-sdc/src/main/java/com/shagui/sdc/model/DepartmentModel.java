package com.shagui.sdc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import com.shagui.sdc.util.jpa.JpaExpirableData;
import com.shagui.sdc.util.jpa.ModelInterface;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a department entity that belongs to a company and contains
 * multiple squads.
 * 
 * Departments are organizational units with attributes such as coverage and
 * trend.
 * 
 * Relationships:
 * - Many-to-One with CompanyModel: Each department belongs to a single company.
 * - One-to-Many with SquadModel: A department can contain multiple squads.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class DepartmentModel implements ModelInterface<Integer>, JpaExpirableData {
	@Id
	private Integer id;

	@Column(nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private CompanyModel company;

	private Float coverage;

	private Float trend;

	@Column(name = "expiry_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;

	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("name ASC")
	private List<SquadModel> squads = new ArrayList<>();

	public DepartmentModel(Integer id) {
		this.id = id;
	}
}
