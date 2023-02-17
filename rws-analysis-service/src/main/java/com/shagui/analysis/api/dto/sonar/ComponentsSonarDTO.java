package com.shagui.analysis.api.dto.sonar;

import java.util.List;

import lombok.Data;

@Data
public class ComponentsSonarDTO {
	private PagingSonarDTO paging;
	private List<ComponentSonarDTO> components;
}
