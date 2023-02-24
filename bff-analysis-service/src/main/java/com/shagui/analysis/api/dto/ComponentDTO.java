package com.shagui.analysis.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentDTO  {
	private Integer id;
	private String name;
	private boolean nonPublic;
	private ComponentTypeDTO componentType;
	private ArchitectureDTO architecture;
	private SquadDTO squad;
}
