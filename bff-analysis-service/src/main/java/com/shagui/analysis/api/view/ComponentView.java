package com.shagui.analysis.api.view;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentView {
	private Integer id;
	private String name;
	private boolean nonPublic;
	private ComponentTypeView componentType;
	private ArchitectureView architecture;
	private SquadView squad;
}
