package com.shagui.sdc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * Represents a squad entity that belongs to a department and contains multiple
 * components.
 * 
 * Squads are organizational units with attributes such as coverage and trend.
 * 
 * Relationships:
 * - Many-to-One with DepartmentModel: Each squad belongs to a single
 * department.
 * - One-to-Many with ComponentModel: A squad can contain multiple components.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "squads")
public class SquadModel implements ModelInterface<Integer>, JpaExpirableData {
	@Id
	private Integer id;

	@Column(nullable = false)
	private String name;

	private Float coverage;

	private Float trend;

	@Column(name = "expiry_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private DepartmentModel department;

	@OneToMany(mappedBy = "squad", fetch = FetchType.LAZY)
	@OrderBy("name ASC")
	private List<ComponentModel> components = new ArrayList<>();

	public SquadModel(Integer id) {
		this.id = id;
	}
}
