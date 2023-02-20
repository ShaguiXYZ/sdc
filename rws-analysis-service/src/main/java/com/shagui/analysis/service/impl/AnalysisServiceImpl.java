package com.shagui.analysis.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.analysis.api.dto.MetricAnalysisDTO;
import com.shagui.analysis.enums.MetricType;
import com.shagui.analysis.model.ComponentAnalysisModel;
import com.shagui.analysis.model.ComponentModel;
import com.shagui.analysis.model.MetricValuesModel;
import com.shagui.analysis.model.pk.ComponentAnalysisPk;
import com.shagui.analysis.repository.ComponentAnalysisRepository;
import com.shagui.analysis.repository.ComponentRepository;
import com.shagui.analysis.repository.JpaCommonRepository;
import com.shagui.analysis.repository.MetricValueRepository;
import com.shagui.analysis.service.AnalysisInterface;
import com.shagui.analysis.service.AnalysisService;
import com.shagui.analysis.util.Mapper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AnalysisServiceImpl implements AnalysisService {
	private JpaCommonRepository<ComponentRepository, ComponentModel, Integer> componentsRepository;
	private JpaCommonRepository<ComponentAnalysisRepository, ComponentAnalysisModel, ComponentAnalysisPk> componentAnalysisRepository;
	private JpaCommonRepository<MetricValueRepository, MetricValuesModel, Integer> metricValuesRepository;

	@Autowired
	private Map<String, AnalysisInterface> metricServices;

	public AnalysisServiceImpl(ComponentRepository componentsRepository,
			ComponentAnalysisRepository componentAnalysisRepository, MetricValueRepository metricValuesRepository) {
		this.componentsRepository = () -> componentsRepository;
		this.componentAnalysisRepository = () -> componentAnalysisRepository;
		this.metricValuesRepository = () -> metricValuesRepository;
	}

	@Override
	public List<MetricAnalysisDTO> analyze(int componentId) {
		ComponentModel component = componentsRepository.findById(componentId);

		List<ComponentAnalysisModel> analysis = executeAsyncMetricServicesAndWait(component);
		List<ComponentAnalysisModel> savedData = saveReturnAnalysis(analysis);

		log.debug("The {} component analysis has been saved. {} records.", component.getName(), savedData.size());

		return savedData.stream().map(transformToMetricAnalysis).collect(Collectors.toList());
	}

	@Override
	public List<MetricAnalysisDTO> metricHistory(int componentId, int metricId, Date date) {
		return componentAnalysisRepository.repository()
				.metricHistory(componentId, metricId, new Timestamp(date.getTime())).stream()
				.map(transformToMetricAnalysis).collect(Collectors.toList());
	}

	@Override
	public List<MetricAnalysisDTO> componentState(int componentId, Date date) {
		return componentAnalysisRepository.repository().componentState(componentId, new Timestamp(date.getTime()))
				.stream().map(transformToMetricAnalysis).collect(Collectors.toList());
	}

	private Function<ComponentAnalysisModel, MetricAnalysisDTO> transformToMetricAnalysis = (analysis) -> {
		List<MetricValuesModel> metricValues = metricValuesRepository.repository().metricValueByDate(
				analysis.getMetric().getId(), analysis.getComponentTypeArchitecture().getId(),
				analysis.getId().getComponentAnalysisDate());

		if (!metricValues.isEmpty()) {
			MetricValuesModel value = metricValues.get(0);
			analysis.setExpectedValue(value.getValue());
			analysis.setGoodValue(value.getGoodValue());
			analysis.setPerfectValue(value.getPerfectValue());
		}

		return Mapper.parse(analysis);
	};

	@Transactional
	private List<ComponentAnalysisModel> saveReturnAnalysis(List<ComponentAnalysisModel> toSave) {
		List<ComponentAnalysisModel> saved = new ArrayList<>();

		if (toSave.size() > 0) {
			saved.addAll(componentAnalysisRepository.repository().saveAll(toSave));
		}

		return saved;
	}

	private List<ComponentAnalysisModel> executeAsyncMetricServicesAndWait(ComponentModel component) {
		Set<MetricType> metricTypes = new HashSet<>();
		component.getComponentTypeArchitecture().getMetrics().forEach(metric -> metricTypes.add(metric.getType()));

		List<Future<List<ComponentAnalysisModel>>> futureTascs = new ArrayList<>();
		metricTypes.forEach(type -> futureTascs
				.add(CompletableFuture.supplyAsync(() -> metricServices.get(type.name()).analyze(component))
						.thenApply(saveChangesInMetrics)));

		while (futureTascs.stream().anyMatch(task -> !task.isDone()))
			;

		List<ComponentAnalysisModel> toSave = new ArrayList<>();
		futureTascs.forEach(task -> {
			try {
				toSave.addAll(task.get());
			} catch (InterruptedException | ExecutionException e) {
				log.error("Error getting task result!!!!!", e);
			}
		});

		return toSave;
	}

	private Function<List<ComponentAnalysisModel>, List<ComponentAnalysisModel>> saveChangesInMetrics = (
			List<ComponentAnalysisModel> toAnalyze) -> {
		List<ComponentAnalysisModel> toSave = toAnalyze.stream().filter(reg -> {
			Optional<ComponentAnalysisModel> model = componentAnalysisRepository.repository()
					.actualMetric(reg.getId().getComponentId(), reg.getId().getMetricId());
			return model.isEmpty() || !model.get().getValue().equals(reg.getValue());
		}).collect(Collectors.toList());

		return toSave;
	};
}
