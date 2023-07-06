package com.shagui.sdc.json.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataListModel {
	private String name;
	private List<String> values = new ArrayList<>();
}
