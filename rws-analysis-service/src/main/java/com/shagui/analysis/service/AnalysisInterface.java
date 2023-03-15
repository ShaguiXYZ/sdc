package com.shagui.analysis.service;

import java.util.List;
import java.util.Optional;

import com.shagui.analysis.enums.UriType;
import com.shagui.analysis.model.ComponentAnalysisModel;
import com.shagui.analysis.model.ComponentModel;
import com.shagui.analysis.model.RequestProperiesModel;
import com.shagui.analysis.model.UriModel;

@FunctionalInterface
public interface AnalysisInterface {
	List<ComponentAnalysisModel> analyze(ComponentModel component);

	default Optional<UriModel> getUri(List<UriModel> uris, UriType type) {
		return uris.stream().filter(uri -> type.equals(uri.getType())).findFirst();
	}

	default Optional<String> getUriProperty(UriModel uri, String key) {
		return uri.getProperties().stream().filter(property -> property.getKey().equals(key))
				.map(RequestProperiesModel::getValue).findFirst();
	}
}
