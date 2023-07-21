package com.shagui.sdc.api.dto.cmdb;

import java.util.List;

import com.shagui.sdc.model.CompanyModel;
import com.shagui.sdc.model.DepartmentModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentInput {
	private int id;
	private String name;
	private int cia;
	private List<SquadInput> squads;

	public DepartmentModel asModel() {
		DepartmentModel model = new DepartmentModel(this.getId());

		return patch(model);
	}
	
	public DepartmentModel patch(DepartmentModel model) {
		model.setName(this.getName());
		model.setCompany(new CompanyModel(this.getCia()));

		return model;		
	}
}
