package com.shagui.sdc.json;

import java.util.ArrayList;
import java.util.List;

import com.shagui.sdc.json.model.ComponentParamsModel;
import com.shagui.sdc.json.model.DataListModel;
import com.shagui.sdc.json.model.DataLists;
import com.shagui.sdc.json.model.UriModel;

public class StaticRepository {

	private StaticRepository() {
	}

	private static StaticRepositoryConfig config;

	protected static void setConfig(StaticRepositoryConfig config) {
		StaticRepository.config = config;
	}

	public static List<UriModel> uris() {
		return config.uris();
	}

	public static List<DataListModel> datalists() {
		return config.datalists();
	}

	public static List<String> datalistValues(DataLists datalist) {
		return config.datalists().stream()
				.filter(d -> d.getName().equals(datalist.getName()))
				.findFirst()
				.map(DataListModel::getValues)
				.orElse(new ArrayList<>());
	}

	public static List<ComponentParamsModel> componentParams() {
		return config.componentParams();
	}
}
