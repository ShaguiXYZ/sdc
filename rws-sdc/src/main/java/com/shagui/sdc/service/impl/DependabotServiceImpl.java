package com.shagui.sdc.service.impl;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.GitClient;
import com.shagui.sdc.api.dto.git.DependabotAlertDTO;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.service.DependabotService;
import com.shagui.sdc.util.ComponentUtils;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.DictioraryReplacement;
import com.shagui.sdc.util.UrlUtils;

import feign.Response;

@Service(Ctes.ANALYSIS_SERVICES_TYPES.DEPENDABOT)
public class DependabotServiceImpl implements DependabotService {
	@Autowired
	private GitClient gitClient;

	@Override
	public List<ComponentAnalysisModel> analyze(ComponentModel component) {
		List<DependabotAlertDTO> result = Arrays.asList(retrieveGitData(component));

		return this.metrics(component).stream().map(metric -> {
			String value = MetricLibrary.Library.valueOf(metric.getValue().toUpperCase())
					.apply(result);

			return new ComponentAnalysisModel(component, metric, value);
		}).collect(Collectors.toList());
	}

	private DependabotAlertDTO[] retrieveGitData(ComponentModel component) {
		Optional<UriModel> uriModel = UrlUtils.componentUri(component, UriType.DEPENDABOT);

		if (uriModel.isPresent()) {
			Optional<String> authorizationHeader = authorization(uriModel.get());
			String uri = uriModel.get().getValue();
			Response response = authorizationHeader.isPresent()
					? gitClient.repoFile(URI.create(uri), authorizationHeader.get())
					: gitClient.repoFile(URI.create(uri));

			return UrlUtils.mapResponse(response, DependabotAlertDTO[].class);
		}

		return new DependabotAlertDTO[0];
	}

	private Optional<String> authorization(UriModel uriModel) {
		Optional<String> authorization = UrlUtils.uriProperty(uriModel, Ctes.URI_PROPERTIES.AUTHORIZATION);

		return authorization.map(data -> DictioraryReplacement.getInstance(ComponentUtils.tokens()).replace(data, ""));
	}

	private static class MetricLibrary {
		enum Library {
			LOW_SEVERITY(MetricLibrary.vulnerabilityCountFn("low")),
			MEDIUM_SEVERITY(MetricLibrary.vulnerabilityCountFn("medium")),
			HIGH_SEVERITY(MetricLibrary.vulnerabilityCountFn("high")),
			CRITICAL_SEVERITY(MetricLibrary.vulnerabilityCountFn("critical"));

			private Function<List<DependabotAlertDTO>, String> fn;

			private Library(Function<List<DependabotAlertDTO>, String> fn) {
				this.fn = fn;
			}

			public String apply(List<DependabotAlertDTO> data) {
				return this.fn.apply(data);
			}
		}

		private MetricLibrary() {
		}

		private static Function<List<DependabotAlertDTO>, String> vulnerabilityCountFn(String severity) {
			return data -> String.valueOf(data.stream().filter(
					alert -> alert.getVulnerability() != null && severity.equals(alert.getVulnerability().getSeverity()))
					.count());
		}
	}
}
