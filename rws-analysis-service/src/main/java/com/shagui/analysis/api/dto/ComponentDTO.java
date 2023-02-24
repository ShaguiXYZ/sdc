package com.shagui.analysis.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComponentDTO  {
	private Integer id;
	private String name;
	private boolean nonPublic;
	private ComponentTypeDTO componentType;
	private ArchitectureDTO architecture;
	private SquadDTO squad;
}
