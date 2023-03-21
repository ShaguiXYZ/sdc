package com.shagui.sdc.api.dto;

import com.shagui.sdc.enums.UriType;

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
