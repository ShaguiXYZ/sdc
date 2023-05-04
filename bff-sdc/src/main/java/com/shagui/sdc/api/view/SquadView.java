package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.dto.SquadDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SquadView {
	private Integer id;
	private String name;
	private DepartmentView department;
	private Float coverage;
	
	public SquadView(SquadDTO source) {
		BeanUtils.copyProperties(source, this);
		
		this.department = CastFactory.getInstance(DepartmentView.class).parse(source.getDepartment());
	}
}
