package com.shagui.analysis.api.dto;

import com.shagui.analysis.enums.UriType;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UriDTO {
	private int id;
	private String name;
	private String uri;
	private UriType type;
}
