package com.shagui.analysis.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ComponentsDTO {	
	private PagingDTO paging;
	@JsonAlias("Data")
	private List<ComponentDTO> components;
}
