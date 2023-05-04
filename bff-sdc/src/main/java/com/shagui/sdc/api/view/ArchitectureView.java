package com.shagui.sdc.api.view;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.dto.ArchitectureDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArchitectureView {
	private Integer id;
	private String name;

	public ArchitectureView(ArchitectureDTO source) {
		BeanUtils.copyProperties(source, this);
	}
}
