package com.shagui.sdc.json.model;

import java.util.List;

import com.shagui.sdc.enums.UriType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UriModel {
	private String name;
	private String api;
	private String url;
	private UriType type;
	private List<RequestPropertiesModel> properties;
}
