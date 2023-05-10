package com.shagui.sdc.api.dto;

import com.shagui.sdc.enums.UriType;

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
	private UriType type;
}
