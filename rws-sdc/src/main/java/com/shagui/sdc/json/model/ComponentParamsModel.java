package com.shagui.sdc.json.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentParamsModel {
	private String type;
	private List<ParamConfigModel> params;
}
