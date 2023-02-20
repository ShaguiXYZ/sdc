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
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "squads")
@NoArgsConstructor
public class SquadModel implements ModelInterface<Integer> {
	
	public SquadModel(Integer id) {
		this.id = id;
	}
	
	@Id
	@Column(name = "squad_id")
	private Integer id;

	@Column(name = "squad_name", nullable = false)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private DepartmentModel department;
		
	@OneToMany(mappedBy = "squad", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<ComponentModel> components;
}
