package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.dto.SquadDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SquadView {
	private Integer id;
	private String name;
	private DepartmentView department;
	private Float coverage;
	private Float trend;

	public SquadView(SquadDTO source) {
		BeanUtils.copyProperties(source, this);

		this.department = CastFactory.getInstance(DepartmentView.class).parse(source.getDepartment());
	}
}
