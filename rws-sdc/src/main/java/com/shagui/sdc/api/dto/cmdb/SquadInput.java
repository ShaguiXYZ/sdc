package com.shagui.sdc.api.dto.cmdb;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SquadInput {
	private Integer id;
	private Integer departmentId;
	private String name;
	private int cia;
	private List<BusinessServiceInput> businessServices;
}
