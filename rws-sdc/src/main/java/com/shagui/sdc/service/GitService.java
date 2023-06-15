package com.shagui.sdc.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.json.data.UriModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.util.ComponentUtils;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.DictioraryReplacement;

public interface GitService extends AnalysisInterface {
	@Override
	default Optional<String> uri(ComponentModel component) {
		String uri = null;
		Optional<UriModel> uriModel = uri(component.getUris(), AnalysisType.GIT);

		if (uriModel.isPresent()) {
			String xmlPath = ComponentUtils.propertyValue(component, Ctes.COMPONENT_PROPERTIES.XML_PATH);
			String path = ComponentUtils.propertyValue(component, Ctes.COMPONENT_PROPERTIES.PATH);
			String owner = ComponentUtils.propertyValue(component, Ctes.COMPONENT_PROPERTIES.COMPONENT_OWNER);
			String repository = ComponentUtils.propertyValue(component, Ctes.COMPONENT_PROPERTIES.COMPONENT_REPOSITORY);

			uri = uriModel.get().getValue();
			uri = Arrays.asList(uri, owner, repository, "contents", path, xmlPath).stream().filter(StringUtils::hasText)
					.collect(Collectors.joining("/"));
		}

		return Optional.ofNullable(uri);
	}

	@Override
	default List<MetricModel> metrics(ComponentModel component) {
		return ComponentUtils.metricsByType(component, AnalysisType.GIT);
	}

	default Optional<String> authorization(ComponentModel component) {
		String authorization = null;
		Optional<UriModel> uriModel = uri(component.getUris(), AnalysisType.GIT);

		if (uriModel.isPresent()) {
			authorization = uriProperty(uriModel.get(), Ctes.URI_PROPERTIES.AUTHORIZATION).orElse(null);
		}

		return Optional.ofNullable(authorization)
				.map(data -> DictioraryReplacement.getInstance(ComponentUtils.tokens()).replace(data));
	}
}
