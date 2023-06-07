package com.shagui.sdc.api.dto.input;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentInput {
	private int id;
	private String name;
	private int cia;
	private List<SquadInput> squads;
}
