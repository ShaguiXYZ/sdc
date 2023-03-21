package com.shagui.sdc.api.dto;

import com.shagui.sdc.api.view.ParseableTo;
import com.shagui.sdc.api.view.SquadView;
import com.shagui.sdc.util.Mapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SquadDTO implements ParseableTo<SquadView> {
	private Integer id;
	private String name;
	private DepartmentDTO department;
	private Float coverage;

	@Override
	public SquadView parse() {
		return Mapper.parse(this);
	}
}
