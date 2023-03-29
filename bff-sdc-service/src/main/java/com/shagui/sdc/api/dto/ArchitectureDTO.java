package com.shagui.sdc.api.dto;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.view.ArchitectureView;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArchitectureDTO {
	private Integer id;
	private String name;

	public ArchitectureDTO(ArchitectureView source) {
		BeanUtils.copyProperties(source, this);
	}
}
