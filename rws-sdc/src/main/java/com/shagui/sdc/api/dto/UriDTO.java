package com.shagui.sdc.api.dto;

import com.shagui.sdc.enums.AnalysisType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class UriDTO {
	private int id;
	private String name;
	private String uri;
	private AnalysisType type;
}
