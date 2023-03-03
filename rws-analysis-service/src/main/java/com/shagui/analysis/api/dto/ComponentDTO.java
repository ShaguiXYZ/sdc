package com.shagui.analysis.api.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
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
	private Date analysisDate;
	private Float coverage;
	private ComponentTypeDTO componentType;
	private ArchitectureDTO architecture;
	private SquadDTO squad;
}
