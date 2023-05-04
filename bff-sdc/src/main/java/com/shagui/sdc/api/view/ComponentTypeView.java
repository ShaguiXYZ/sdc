package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.dto.ComponentTypeDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentTypeView {
	private Integer id;
	private String name;

	public ComponentTypeView(ComponentTypeDTO source) {
		BeanUtils.copyProperties(source, this);
	}
}
