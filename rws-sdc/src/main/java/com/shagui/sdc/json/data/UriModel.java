package com.shagui.sdc.json.data;

import java.util.List;

import com.shagui.sdc.enums.AnalysisType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UriModel {
	private String name;
	private String value;
	private AnalysisType type;
	private List<RequestPropertiesModel> properties;
}
