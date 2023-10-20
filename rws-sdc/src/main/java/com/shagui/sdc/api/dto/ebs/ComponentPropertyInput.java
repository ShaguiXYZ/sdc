package com.shagui.sdc.api.dto.ebs;

import com.shagui.sdc.model.ComponentPropertyModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComponentPropertyInput {
	private String name;
	private String value;
	private boolean toDelete;

	public ComponentPropertyModel asModel() {
		ComponentPropertyModel model = new ComponentPropertyModel();
		model.setName(this.getName());
		model.setValue(this.getValue());

		return model;
	}
}
