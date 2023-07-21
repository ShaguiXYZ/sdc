package com.shagui.sdc.api.dto.cmdb;

import java.util.List;

import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.model.SquadModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SquadInput {
	private Integer id;
	private Integer departmentId;
	private String name;
	private int cia;
	private List<BusinessServiceInput> businessServices;
	
	public SquadModel toModel() {
		SquadModel model = new SquadModel(this.getId());
		
		return patch(model);
	}
	
	public SquadModel patch(SquadModel model) {
		model.setName(this.getName());
		model.setDepartment(new DepartmentModel(this.getDepartmentId()));

		return model;		
	}
}
