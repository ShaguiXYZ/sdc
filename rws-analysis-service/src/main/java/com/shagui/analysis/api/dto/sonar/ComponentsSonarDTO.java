package com.shagui.analysis.api.dto.sonar;

import java.util.List;

import com.shagui.analysis.api.dto.PagingDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentsSonarDTO {
	private PagingDTO paging;
	private List<ComponentSonarDTO> components;
}