package com.shagui.sdc.api.dto;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.view.ComponentTypeView;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentTypeDTO {
	private Integer id;
	private String name;

	public ComponentTypeDTO(ComponentTypeView source) {
		BeanUtils.copyProperties(source, this);
	}
}
