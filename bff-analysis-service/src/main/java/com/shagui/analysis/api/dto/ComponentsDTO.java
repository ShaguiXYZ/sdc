package com.shagui.analysis.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComponentsDTO {	
	private PagingDTO paging;
	private List<ComponentDTO> components;
}
