package com.shagui.sdc.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.shagui.sdc.api.dto.git.DependabotAlertDTO;
import com.shagui.sdc.api.dto.sse.EventFactory;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.service.AnalysisInterface;
import com.shagui.sdc.service.SseService;
import com.shagui.sdc.util.ComponentUtils;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.git.GitUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service(Ctes.AnalysisServicesTypes.DEPENDABOT)
public class DependabotServiceImpl implements AnalysisInterface {
	private final SseService sseService;

	@Override
	public List<MetricModel> metrics(ComponentModel component) {
		return ComponentUtils.metricsByType(component, AnalysisType.DEPENDABOT);
	}

	@Override
	public List<ComponentAnalysisModel> analyze(String workflowId, ComponentModel component) {
		try {
			List<DependabotAlertDTO> result = Stream.iterate(1, page -> ++page)
					.map(page -> {
						Map<String, String> uriParams = new HashMap<>();
						uriParams.put("page", Integer.toString(page));

						Optional<DependabotAlertDTO[]> alerts = GitUtils.retrieveGitData(component,
								GitUtils.GitOperations.DEPENDABOT_ALERTS, uriParams,
								DependabotAlertDTO[].class);

						return alerts.map(Arrays::asList).orElse(Collections.emptyList());
					})
					.takeWhile(data -> !CollectionUtils.isEmpty(data))
					.flatMap(List::stream)
					.toList();

			return this.metrics(component).stream()
					.map(metric -> {
						String value = MetricLibrary.Library.valueOf(metric.getValue().toUpperCase()).apply(result);
						return new ComponentAnalysisModel(component, metric, value);
					})
					.toList();
		} catch (SdcCustomException e) {
			sseService.emitError(EventFactory.event(workflowId, e).referencedBy(component));
			return List.of();
		}
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
