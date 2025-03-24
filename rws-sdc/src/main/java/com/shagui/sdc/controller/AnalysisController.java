package com.shagui.sdc.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.AnalysisRestApi;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.AnalysisValuesDTO;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.service.AnalysisService;
import com.shagui.sdc.service.ComponentService;
import com.shagui.sdc.service.MetricService;
import com.shagui.sdc.util.DateUtils;
import com.shagui.sdc.util.collector.SdcCollectors;
import com.shagui.sdc.util.validations.Mergeable;
import com.shagui.sdc.util.validations.ValidationsUtils;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "analysis", description = "API to analyze components")
public class AnalysisController implements AnalysisRestApi {
	private AnalysisService analysisService;
	private ComponentService componentService;
	private MetricService metricService;

	@Override
	public PageData<MetricAnalysisDTO> analysis(int componentId) {
		return analysisService.analysis(componentId);
	}

	@Override
	public MetricAnalysisDTO analysis(int componentId, int metricId) {
		return analysisService.analysis(componentId, metricId);
	}

	@Override
	public PageData<MetricAnalysisDTO> metricHistory(int componentId, int metricId, Date from, Integer page,
			Integer ps) {
		if (page == null) {
			return analysisService.metricHistory(componentId, metricId, from == null ? new Date() : from);
		} else {
			return analysisService.metricHistory(componentId, metricId, from == null ? new Date() : from,
					new RequestPageInfo(page, ps));
		}
	}

	@Override
	public PageData<MetricAnalysisDTO> metricHistory(int componentId, String metricName, AnalysisType type, Date from,
			Integer page, Integer ps) {
		if (page == null) {
			return analysisService.metricHistory(componentId, metricName, type, from == null ? new Date() : from);
		} else {
			return analysisService.metricHistory(componentId, metricName, type, from == null ? new Date() : from,
					new RequestPageInfo(page, ps));
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageData<MetricAnalysisDTO> annualSum(String metricName, AnalysisType metricType, Integer componentId,
			Integer squadId, Integer departmentId) {
		MetricDTO metric = metricService.metric(metricName, metricType);

		if (Mergeable.class.isAssignableFrom(metric.getValueType().clazz())) {
			List<Date> lastTwelveMonths = DateUtils.getLastMounth(-12);

			return lastTwelveMonths.stream().flatMap(date -> {
				PageData<MetricAnalysisDTO> pageData = analysisService.filterAnalysis(metric.getId(),
						componentId, squadId, departmentId, date);

				return Optional.ofNullable(merge(pageData.getPage(), metric.getValueType().clazz()))
						.map(mergeable -> new MetricAnalysisDTO(date, metric,
								new AnalysisValuesDTO(0, mergeable.toString(), null, null, null), null, false))
						.stream();
			}).collect(SdcCollectors.toPageable());
		}

		return PageData.empty();
	}

	@Override
	public PageData<MetricAnalysisDTO> analyze(int squadId, String componentName) {
		ComponentDTO component = componentService.findBy(squadId, componentName);

		return analyze(component.getId());
	}

	@Override
	public PageData<MetricAnalysisDTO> analyze(int componentId) {
		if (!analysisService.analyze(componentId).getPage().isEmpty()) {
			analysisService.updateTrend(componentId);
		}

		return analysisService.analysis(componentId);
	}

	/**
	 * Merges a list of MetricAnalysisDTO objects into a single instance of the
	 * specified class.
	 *
	 * @param <T>   the type of the class that extends Mergeable
	 * @param page  the list of MetricAnalysisDTO objects to be merged
	 * @param clazz the class of the type T
	 * @return a single instance of type T that is the result of merging all the
	 *         MetricAnalysisDTO objects
	 */
	private <T extends Mergeable<T>> T merge(List<MetricAnalysisDTO> page, Class<T> clazz) {
		return page.stream()
				.map(data -> ValidationsUtils.cast(data.getAnalysisValues().getMetricValue(), clazz))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.reduce(null, (a, b) -> {
					if (a == null) {
						return b;
					}

					a.merge(b);

					return a;
				});
	}
}
