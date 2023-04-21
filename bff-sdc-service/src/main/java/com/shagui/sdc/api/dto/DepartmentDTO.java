package com.shagui.sdc.api.dto;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.view.DepartmentView;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDTO {
	private int id;
	private String name;
	private Float coverage;

	public DepartmentDTO(DepartmentView source) {
		BeanUtils.copyProperties(source, this);
	}
}
