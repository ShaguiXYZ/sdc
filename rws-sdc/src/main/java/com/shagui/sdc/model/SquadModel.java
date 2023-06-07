package com.shagui.sdc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@Column(name = "expiry_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private DepartmentModel department;

	@OneToMany(mappedBy = "squad", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<ComponentModel> components =  new ArrayList<>();

	public SquadModel(Integer id) {
		this.id = id;
	}
}
