package com.shagui.sdc.service;

import java.util.List;

import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.model.UriModel;

public interface UriService {
	List<UriModel> availables();

	UriModel componentUri(int componentId, UriType type);
}
