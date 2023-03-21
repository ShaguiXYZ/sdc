package com.shagui.sdc.api.view;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SquadView {
	private Integer id;
	private String name;
	private DepartmentView department;
	private Float coverage;
}
