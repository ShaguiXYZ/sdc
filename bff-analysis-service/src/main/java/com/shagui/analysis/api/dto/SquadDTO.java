package com.shagui.analysis.api.dto;

import com.shagui.analysis.api.view.ParseableTo;
import com.shagui.analysis.api.view.SquadView;
import com.shagui.analysis.util.Mapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SquadDTO implements ParseableTo<SquadView> {
	private Integer id;
	private String name;
	private DepartmentDTO department;

	@Override
	public SquadView parse() {
		return Mapper.parse(this);
	}
}
