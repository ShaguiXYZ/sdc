package com.shagui.sdc.api.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComponentDTO {
	private Integer id;
	private String name;
	private ComponentTypeArchitectureDTO componentTypeArchitecture;
	private Date analysisDate;
	private Float coverage;
	private boolean blocked;
	private SquadDTO squad;
	private List<TagDTO> tags;
}
