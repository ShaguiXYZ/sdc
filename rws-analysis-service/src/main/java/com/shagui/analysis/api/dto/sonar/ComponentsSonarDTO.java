package com.shagui.analysis.api.dto.sonar;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentsSonarDTO {
	private PagingSonarDTO paging;
	private List<ComponentSonarDTO> components;
}
