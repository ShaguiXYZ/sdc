package com.shagui.sdc.api.dto;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.view.SquadView;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SquadDTO {
	private Integer id;
	private String name;
	private DepartmentDTO department;
	private Float coverage;

	public SquadDTO(SquadView source) {
		BeanUtils.copyProperties(source, this);

		this.department = CastFactory.getInstance(DepartmentDTO.class).parse(source.getDepartment());
	}
}
