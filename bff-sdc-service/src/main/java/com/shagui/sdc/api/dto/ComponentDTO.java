package com.shagui.sdc.api.dto;

import java.util.Date;

import com.shagui.sdc.api.view.ComponentView;
import com.shagui.sdc.api.view.ParseableTo;
import com.shagui.sdc.util.Mapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentDTO implements ParseableTo<ComponentView> {
	private Integer id;
	private String name;
	private Date analysisDate;
	private Float coverage;
	private ComponentTypeDTO componentType;
	private ArchitectureDTO architecture;
	private SquadDTO squad;
	
	@Override
	public ComponentView parse() {
		return Mapper.parse(this);
	}
}
