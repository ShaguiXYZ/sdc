package com.shagui.sdc.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.dto.ServiceDataDTO;
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
import com.shagui.sdc.util.git.lib.GitLib;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service(Ctes.AnalysisServicesTypes.GIT)
public class GitServiceImpl implements AnalysisInterface {
	private final SseService sseService;

	@Override
	public List<MetricModel> metrics(ComponentModel component) {
		return ComponentUtils.metricsByType(component, AnalysisType.GIT);
	}

	@Override
	public List<ComponentAnalysisModel> analyze(String workflowId, ComponentModel component) {
		List<MetricModel> gitMetrics = metrics(component);

		return gitMetrics.parallelStream().map(metric -> {
			try {
				return MetricLibrary.Library.valueOf(metric.getValue().toUpperCase())
						.apply(new ServiceDataDTO(workflowId, component, metric))
						.map(value -> new ComponentAnalysisModel(component, metric, value)).orElse(null);
			} catch (SdcCustomException e) {
				sseService.emitError(EventFactory.event(workflowId, e).referencedBy(component, metric));
				return null;
			}
		}).filter(Objects::nonNull).toList();
	}

	private static class MetricLibrary {
		enum Library {
			NCLOC_LANGUAGE_DISTRIBUTION(GitLib.nclocLanguageDistribution), LINES(GitLib.lines);

			private Function<ServiceDataDTO, Optional<String>> fn;

			private Library(Function<ServiceDataDTO, Optional<String>> fn) {
				this.fn = fn;
			}

			public Optional<String> apply(ServiceDataDTO data) {
				return this.fn.apply(data);
			}
		}
	}
}
