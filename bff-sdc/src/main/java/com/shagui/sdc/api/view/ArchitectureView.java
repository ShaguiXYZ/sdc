package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.dto.ArchitectureDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArchitectureView {
	private Integer id;
	private String name;

	public ArchitectureView(ArchitectureDTO source) {
		BeanUtils.copyProperties(source, this);
	}
}
