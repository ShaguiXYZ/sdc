package com.shagui.sdc.api.dto.sonar;

import java.util.List;

import com.shagui.sdc.api.domain.PageInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentsSonarDTO {
	private PageInfo paging;
	private List<ComponentSonarDTO> components;
}
