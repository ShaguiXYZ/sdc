package com.shagui.analysis.api.view;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentView {
	private Integer id;
	private String name;
	private boolean nonPublic;
	private Date analysisDate;
	private Float coverage;
	private ComponentTypeView componentType;
	private ArchitectureView architecture;
	private SquadView squad;
}
