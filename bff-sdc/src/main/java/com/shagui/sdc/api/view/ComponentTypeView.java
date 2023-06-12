package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.dto.ComponentTypeDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComponentTypeView {
	private Integer id;
	private String name;

	public ComponentTypeView(ComponentTypeDTO source) {
		BeanUtils.copyProperties(source, this);
	}
}
