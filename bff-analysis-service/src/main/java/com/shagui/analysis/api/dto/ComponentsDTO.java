package com.shagui.analysis.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ComponentsDTO {
	private PagingDTO paging;
	private List<ComponentDTO> components;

	@JsonProperty("data")
	public void setComponets(List<ComponentDTO> components) {
		this.components = components;
	}
}
