package com.shagui.sdc.service;

import java.util.List;
import java.util.Optional;

import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.data.RequestPropertiesModel;
import com.shagui.sdc.json.data.UriModel;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentUris;
import com.shagui.sdc.model.MetricModel;

public interface AnalysisInterface {
	List<ComponentAnalysisModel> analyze(ComponentModel component);

	Optional<String> uri(ComponentModel component);

	List<MetricModel> metrics(ComponentModel component);

	default Optional<UriModel> uri(List<ComponentUris> uris, AnalysisType type) {
		return StaticRepository.uris().stream().filter(uri -> type.equals(uri.getType()))
				.filter(uri -> uris.stream().anyMatch(u -> u.getId().getUriName().equals(uri.getName()))).findFirst();
	}

	default Optional<String> uriProperty(UriModel uri, String key) {
		return uri.getProperties().stream().filter(property -> property.getName().equals(key))
				.map(RequestPropertiesModel::getValue).findFirst();
	}
}
