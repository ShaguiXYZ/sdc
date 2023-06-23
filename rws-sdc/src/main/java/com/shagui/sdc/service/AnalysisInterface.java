package com.shagui.sdc.service;

import java.util.List;
import java.util.Optional;

import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.model.RequestPropertiesModel;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentUrisModel;
import com.shagui.sdc.model.MetricModel;

public interface AnalysisInterface {
	List<ComponentAnalysisModel> analyze(ComponentModel component);

	Optional<String> uri(ComponentModel component);

	List<MetricModel> metrics(ComponentModel component);

	default Optional<UriModel> uri(List<ComponentUrisModel> uris, AnalysisType type) {
		return StaticRepository.uris().stream().filter(uri -> type.equals(uri.getType()))
				.filter(uri -> uris.stream().anyMatch(u -> u.getId().getUriName().equals(uri.getName()))).findFirst();
	}

	default Optional<String> uriProperty(UriModel uri, String key) {
		return uri.getProperties().stream().filter(property -> property.getName().equals(key))
				.map(RequestPropertiesModel::getValue).findFirst();
	}
}
