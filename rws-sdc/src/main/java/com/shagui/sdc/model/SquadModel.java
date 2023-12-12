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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "squads")
@NoArgsConstructor
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

	@OneToMany(mappedBy = "squad", fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("name ASC")
	private List<ComponentModel> components = new ArrayList<>();

	public SquadModel(Integer id) {
		this.id = id;
	}
}
