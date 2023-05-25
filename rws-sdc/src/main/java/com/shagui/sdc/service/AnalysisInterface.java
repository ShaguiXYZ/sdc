package com.shagui.sdc.service;

import java.util.List;
import java.util.Optional;

import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.RequestPropertiesModel;
import com.shagui.sdc.model.UriModel;

public interface AnalysisInterface {
	List<ComponentAnalysisModel> analyze(ComponentModel component);
	
	Optional<String> uri(ComponentModel component);
	
	List<MetricModel> metrics(ComponentModel component);

	default Optional<UriModel> uri(List<UriModel> uris, AnalysisType type) {
		return uris.stream().filter(uri -> type.equals(uri.getType())).findFirst();
	}

	default Optional<String> uriProperty(UriModel uri, String key) {
		return uri.getProperties().stream().filter(property -> property.getKey().equals(key))
				.map(RequestPropertiesModel::getValue).findFirst();
	}
}
