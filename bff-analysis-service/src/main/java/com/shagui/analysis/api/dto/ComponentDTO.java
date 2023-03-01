package com.shagui.analysis.api.dto;

import com.shagui.analysis.api.view.ComponentView;
import com.shagui.analysis.api.view.ParseableTo;
import com.shagui.analysis.util.Mapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentDTO implements ParseableTo<ComponentView> {
	private Integer id;
	private String name;
	private boolean nonPublic;
	private ComponentTypeDTO componentType;
	private ArchitectureDTO architecture;
	private SquadDTO squad;
	
	@Override
	public ComponentView parse() {
		return Mapper.parse(this);
	}
}
