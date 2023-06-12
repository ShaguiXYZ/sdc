package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.dto.DepartmentDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentView {
	private int id;
	private String name;
	private Float coverage;

	public DepartmentView(DepartmentDTO source) {
		BeanUtils.copyProperties(source, this);
	}
}
