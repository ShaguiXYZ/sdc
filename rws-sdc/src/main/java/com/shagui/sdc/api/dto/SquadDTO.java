package com.shagui.sdc.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SquadDTO {
	private Integer id;
	private String name;
	private DepartmentDTO department;
	private Float coverage;
	private Float trend;
}
