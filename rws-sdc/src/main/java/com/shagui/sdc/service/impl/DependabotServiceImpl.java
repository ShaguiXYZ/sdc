package com.shagui.sdc.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.dto.git.DependabotAlertDTO;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.service.DependabotService;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.git.GitUtils;

@Service(Ctes.ANALYSIS_SERVICES_TYPES.DEPENDABOT)
public class DependabotServiceImpl implements DependabotService {

	@Override
	public List<ComponentAnalysisModel> analyze(ComponentModel component) {
		List<DependabotAlertDTO> result = GitUtils
				.retrieveGitData(component, GitUtils.GitOperations.DEPENDABOT_ALERTS, DependabotAlertDTO[].class)
				.map(Arrays::asList).orElseGet(() -> new ArrayList<DependabotAlertDTO>());

		return this.metrics(component).stream().map(metric -> {
			String value = MetricLibrary.Library.valueOf(metric.getValue().toUpperCase()).apply(result);

			return new ComponentAnalysisModel(component, metric, value);
		}).toList();
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
			return data -> String.valueOf(data.stream().filter(alert -> alert.getVulnerability() != null
					&& severity.equals(alert.getVulnerability().getSeverity())).count());
		}
	}
}
