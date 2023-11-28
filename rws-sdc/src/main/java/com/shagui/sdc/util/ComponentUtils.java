package com.shagui.sdc.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.enums.MetricValueType;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
import com.shagui.sdc.model.ComponentTagModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.model.TagModel;
import com.shagui.sdc.model.pk.ComponentTagPk;
import com.shagui.sdc.util.validations.NumericMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComponentUtils {
	private static ComponentUtilsConfig config;

	private ComponentUtils() {
	}

	public static void setConfig(ComponentUtilsConfig config) {
		ComponentUtils.config = config;
	}

	public static Map<String, String> tokens() {
		return config.tokens();
	}

	public static Optional<String> componentType(ComponentModel component) {
		String key = null;
		Pattern p = Pattern.compile("^[^\\_\\-]*");
		Matcher m = p.matcher(component.getName());

		if (m.find()) {
			key = m.group();
		}

		return Optional.ofNullable(key);
	}

	@Transactional
	public static void updateRelatedComponentEntities(ComponentModel component, boolean hasNewAnalysis) {
		if (hasNewAnalysis) {
			updateComponent(component);
			updateComponentSquad(component);
			refreshTags(component);
		}

		component.setAnalysisDate(new Date());

		config.componentRepository().update(component.getId(), component);
		config.squadRepository().update(component.getSquad().getId(), component.getSquad());
	}

	public static Optional<ComponentPropertyModel> propertyValue(ComponentModel component, String key) {
		return component.getProperties().stream().filter(property -> key.equals(property.getName())).findFirst();
	}

	public static List<MetricModel> metricsByType(ComponentModel component, AnalysisType type) {
		return component.getComponentTypeArchitecture().getMetrics().stream()
				.filter(metric -> type.equals(metric.getType())).toList();
	}

	public static Map<String, String> dictionaryOf(ComponentModel component) {
		Map<String, String> dictionay = component.getProperties().stream().collect(
				Collectors.toMap(ComponentPropertyModel::getName, ComponentPropertyModel::getValue, (data1, data2) -> {
					log.warn("duplicate key found!");
					return data2;
				}));
		dictionay.put("$name", component.getName());

		return dictionay;
	}

	public static void addOrUpdatePropertyValue(ComponentModel component, String propertyName, String propertyValue) {
		Optional<ComponentPropertyModel> propertyModel = config.componentPropertyRepository().repository()
				.findByComponent_IdAndName(component.getId(), propertyName);

		if (propertyModel.isPresent()) {
			propertyModel.get().setValue(propertyValue);
			config.componentPropertyRepository().update(propertyModel.get().getId(), propertyModel.get());
		} else {
			ComponentPropertyModel property = new ComponentPropertyModel(component, propertyName, propertyValue);
			config.componentPropertyRepository().create(property);
		}
	}

	private static void saveHistoricalCoverage(ComponentModel component, Date date, Float coverage) {
		config.historicalCoverageComponentRepository()
				.create(new ComponentHistoricalCoverageModel(component, date, coverage));
	}

	private static void updateComponent(ComponentModel component) {
		Date date = new Date();

		List<ComponentAnalysisModel> metricAnalysis = config.componentAnalysisRepository().repository()
				.componentAnalysis(component.getId(), new Timestamp(date.getTime()));

		Float coverage = AnalysisUtils.metricCoverage(metricAnalysis);

		if (!coverage.equals(component.getCoverage())) {
			component.setCoverage(coverage);
			component.setBlocked(metricAnalysis.stream().anyMatch(ComponentAnalysisModel::isBlocker));
			saveHistoricalCoverage(component, date, coverage);
		}
	}

	private static void updateComponentSquad(ComponentModel component) {
		Date date = new Date();
		SquadModel squad = component.getSquad();
		List<ComponentAnalysisModel> metricAnalysis = config.componentAnalysisRepository().repository()
				.squadAnalysis(squad.getId(), new Timestamp(date.getTime()));
		Float coverage = AnalysisUtils.metricCoverage(metricAnalysis);

		squad.setCoverage(coverage);
	}

	private static void refreshTags(ComponentModel component) {
		List<ComponentAnalysisModel> metricAnalysis = config.componentAnalysisRepository().repository()
				.componentAnalysis(component.getId(), new Timestamp(new Date().getTime()));

		Map<String, ComponentTagModel> tags = metricAnalysis.stream()
				.filter(analysis -> MetricValueType.NUMERIC_MAP.equals(analysis.getMetric().getValueType()))
				.flatMap(analysis -> {
					NumericMap numericMap = new NumericMap(analysis.getMetricValue());
					return numericMap.getValues().entrySet().stream().map(entry -> {
						ComponentTagModel data = new ComponentTagModel();
						TagModel tag = config.tagRepository().repository().findByName(entry.getKey().toLowerCase())
								.orElseGet(() -> {
									TagModel newTag = new TagModel();
									newTag.setName(entry.getKey().toLowerCase());
									return config.tagRepository().create(newTag);
								});

						data.setId(new ComponentTagPk(component.getId(), tag.getId()));
						data.setComponent(component);
						data.setTag(tag);
						data.setAnalysisTag(true);

						return data;
					});
				})
				.collect(Collectors.toMap(data -> data.getTag().getName(), data -> data, (data1, data2) -> data1));

		config.componentTagRepository().repository().deleteByComponent_IdAndAnalysisTag(component.getId(), true);
		config.componentTagRepository().repository().saveAll(tags.values());
	}
}
