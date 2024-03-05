package com.shagui.sdc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.dto.ServiceDataDTO;
import com.shagui.sdc.api.dto.sse.EventFactory;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.service.GitService;
import com.shagui.sdc.service.SseService;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.git.lib.GitLib;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service(Ctes.AnalysisServicesTypes.GIT)
public class GitServiceImpl implements GitService {
	private final SseService sseService;

	@Override
	public List<ComponentAnalysisModel> analyze(String workflowId, ComponentModel component) {
		List<ComponentAnalysisModel> analysisList = new ArrayList<>();
		List<MetricModel> gitMetrics = metrics(component);

		if (!gitMetrics.isEmpty()) {
			analysisList = gitMetrics.parallelStream().map(metric -> {
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

		return analysisList;
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
