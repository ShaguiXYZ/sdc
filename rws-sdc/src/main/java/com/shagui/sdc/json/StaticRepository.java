package com.shagui.sdc.json;

import java.util.List;

import com.shagui.sdc.json.data.DataListModel;
import com.shagui.sdc.json.data.UriModel;

public class StaticRepository {

	private StaticRepository() {
	}

	private static StaticRepositoryConfig config;

	public static void setConfig(StaticRepositoryConfig config) {
		StaticRepository.config = config;
	}

	public static List<UriModel> uris() {
		return config.uris();
	}

	public static List<DataListModel> datalists() {
		return config.datalists();
	}
}
