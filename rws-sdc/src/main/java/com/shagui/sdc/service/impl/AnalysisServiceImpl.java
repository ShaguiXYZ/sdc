package com.shagui.sdc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.core.exception.ExceptionCodes;
import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.pk.ComponentAnalysisPk;
import com.shagui.sdc.repository.ComponentAnalysisRepository;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.MetricRepository;
import com.shagui.sdc.service.AnalysisInterface;
import com.shagui.sdc.service.AnalysisService;
import com.shagui.sdc.util.AnalysisUtils;
import com.shagui.sdc.util.ComponentUtils;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AnalysisServiceImpl implements AnalysisService {
	private Map<String, AnalysisInterface> metricServices;
	private JpaCommonRepository<ComponentRepository, ComponentModel, Integer> componentsRepository;
	private JpaCommonRepository<ComponentAnalysisRepository, ComponentAnalysisModel, ComponentAnalysisPk> componentAnalysisRepository;
	private JpaCommonRepository<MetricRepository, MetricModel, Integer> metricRepository;

	public AnalysisServiceImpl(Map<String, AnalysisInterface> metricServices, ComponentRepository componentsRepository,
			ComponentAnalysisRepository componentAnalysisRepository,
			MetricRepository metricRepository) {
		this.metricServices = metricServices;
		this.componentsRepository = () -> componentsRepository;
		this.componentAnalysisRepository = () -> componentAnalysisRepository;
		this.metricRepository = () -> metricRepository;
	}

	@Override
	public PageData<MetricAnalysisDTO> analysis(int componentId) {
		List<ComponentAnalysisModel> model = componentAnalysisRepository.repository().componentAnalysis(componentId,
				new Timestamp(new Date().getTime()));

		return model.stream().map(AnalysisUtils.setMetricValues).map(Mapper::parse)
				.collect(SdcCollectors.toPageable());
	}

	@Override
	public MetricAnalysisDTO analysis(int componentId, int metricId) {
		ComponentAnalysisModel model = componentAnalysisRepository.repository().actualMetric(componentId, metricId)
				.map(AnalysisUtils.setMetricValues)
				.orElseThrow(() -> new JpaNotFoundException(ExceptionCodes.NOT_FOUND_ANALYSIS,
						"no result found for metric %s of component %s".formatted(metricId, componentId)));

		return Mapper.parse(model);
	}

	@Override
	@Transactional
	public PageData<MetricAnalysisDTO> analyze(int componentId) {
		ComponentModel component = componentsRepository.findExistingId(componentId);

		List<ComponentAnalysisModel> savedData = executeAsyncMetricServicesAndWait(component)
				.filter(modifiedAnalysis).map(componentAnalysisRepository::save).toList();

		ComponentUtils.updateRelatedComponentEntities(component, !savedData.isEmpty());

		log.debug("The {} component analysis has been saved. {} records.", component.getName(), savedData.size());

		return savedData.stream().map(Mapper::parse).collect(SdcCollectors.toPageable());
	}

	@Override
	public PageData<MetricAnalysisDTO> metricHistory(int componentId, int metricId, Date date) {
		return componentAnalysisRepository.repository()
				.historyOfMetricByDate(componentId, metricId, new Timestamp(date.getTime())).stream()
				.map(AnalysisUtils.setMetricValues).map(Mapper::parse).collect(SdcCollectors.toPageable());
	}

	@Override
	public PageData<MetricAnalysisDTO> metricHistory(int componentId, int metricId, Date date,
			RequestPageInfo pageInfo) {
		Page<ComponentAnalysisModel> data = componentAnalysisRepository.repository()
				.metricHistory(componentId, metricId, new Timestamp(date.getTime()), pageInfo.getPageable());

		return data.stream()
				.map(AnalysisUtils.setMetricValues).map(Mapper::parse).collect(SdcCollectors.toPageable(data));

	}

	@Override
	public PageData<MetricAnalysisDTO> metricHistory(int componentId, String metricName, AnalysisType type, Date date) {
		return metricRepository.repository().findByNameIgnoreCaseAndType(metricName, type)
				.map(metric -> metricHistory(componentId, metric.getId(), date))
				.orElseGet(PageData::empty);
	}

	@Override
	public PageData<MetricAnalysisDTO> metricHistory(int componentId, String metricName, AnalysisType type, Date date,
			RequestPageInfo pageInfo) {
		return metricRepository.repository().findByNameIgnoreCaseAndType(metricName, type)
				.map(metric -> metricHistory(componentId, metric.getId(), date, pageInfo))
				.orElseGet(PageData::empty);
	}

	@Override
	public PageData<MetricAnalysisDTO> filterAnalysis(Integer metricId, Integer componentId,
			Integer squadId, Integer departmentId, Date date) {
		return componentAnalysisRepository.repository()
				.filterAnalysis(metricId, componentId, squadId, departmentId, new Timestamp(date.getTime()))
				.stream()
				.map(AnalysisUtils.setMetricValues).map(Mapper::parse).collect(SdcCollectors.toPageable());
	}

	private Stream<ComponentAnalysisModel> executeAsyncMetricServicesAndWait(ComponentModel component) {
		Set<AnalysisType> metricTypes = component.getComponentTypeArchitecture().getMetrics().stream()
				.map(MetricModel::getType).collect(Collectors.toSet());

		List<ComponentAnalysisModel> toSave = new ArrayList<>();

		metricTypes.parallelStream().forEach(type -> {
			try {
				toSave.addAll(metricServices.get(type.name()).analyze(component));
			} catch (SdcCustomException e) {
				log.error("Error getting task result!!!!!", e);
			}
		});

		return toSave.stream().map(data -> {
			ComponentAnalysisModel model = AnalysisUtils.setMetricValues.apply(data);
			model.setBlocker(isBlockerAnaysis(model));

			return model;
		});
	}

	private Predicate<ComponentAnalysisModel> modifiedAnalysis = reg -> componentAnalysisRepository.repository()
			.actualMetric(reg.getId().getComponentId(), reg.getId().getMetricId()).map(AnalysisUtils.setMetricValues)
			.map(model -> !Objects.equals(model.getMetricValue(), reg.getMetricValue())
					|| !Objects.equals(model.getCoverage(), reg.getCoverage())
					|| !Objects.equals(model.getExpectedValue(), reg.getExpectedValue())
					|| !Objects.equals(model.getGoodValue(), reg.getGoodValue())
					|| !Objects.equals(model.getPerfectValue(), reg.getPerfectValue())
					|| model.isBlocker() != reg.isBlocker())
			.orElse(true);

	private static Boolean isBlockerAnaysis(ComponentAnalysisModel analysis) {
		return analysis.getMetric().isBlocker() && Objects.nonNull(analysis.getCoverage())
				&& analysis.getCoverage() < 50;
	}
}
