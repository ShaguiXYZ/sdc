package com.shagui.sdc.service;

import java.util.List;
import java.util.Optional;

import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.json.data.UriModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.util.ComponentUtils;

public interface SonarService extends AnalysisInterface {
	@Override
	default Optional<String> uri(ComponentModel component) {
		String uri = null;
		Optional<UriModel> uriModel = uri(component.getUris(), AnalysisType.SONAR);
		
		if (uriModel.isPresent()) {
			uri= uriModel.get().getValue();
		}
		
		return Optional.ofNullable(uri);
	}

	@Override
	default List<MetricModel> metrics(ComponentModel component) {
		return ComponentUtils.metricsByType(component, AnalysisType.SONAR);
	}
}
