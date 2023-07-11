package com.shagui.sdc.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.SquadModel;

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

	@Transactional
	public static void updateComponentProperties(ComponentModel component) {
		Date date = new Date();

		addOrUpdatePropertyValue(component, Ctes.COMPONENT_PROPERTIES.COMPONENT_ANALYSIS_DATE,
				Long.toString(date.getTime()));
	}

	@Transactional
	public static void updateRelatedComponentEntities(ComponentModel component) {
		updateComponent(component);
		updateSquadComponent(component);
	}

	public static Optional<ComponentPropertyModel> propertyValue(ComponentModel component, String key) {
		return component.getProperties().stream().filter(property -> key.equals(property.getName())).findFirst();
	}

	public static List<MetricModel> metricsByType(ComponentModel component, AnalysisType type) {
		return component.getComponentTypeArchitecture().getMetrics().stream()
				.filter(metric -> type.equals(metric.getType())).collect(Collectors.toList());
	}
	
	public static Map<String, String> dictionaryOf(ComponentModel component) {
		Map<String, String> dictionay = new HashMap<>();
		dictionay.put("$name", component.getName());

		component.getProperties().forEach(property -> dictionay.put(property.getName(), property.getValue()));
		return dictionay;
	}

	private static void addOrUpdatePropertyValue(ComponentModel component, String propertyName, String propertyValue) {
		Optional<ComponentPropertyModel> propertyModel = component.getProperties().stream()
				.filter(property -> propertyName.equals(property.getName())).findFirst();

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

		component.setCoverage(coverage);
		component = config.componentRepository().update(component.getId(), component);

		saveHistoricalCoverage(component, date, coverage);
	}

	private static void updateSquadComponent(ComponentModel component) {
		Date date = new Date();
		SquadModel squad = component.getSquad();

		List<ComponentAnalysisModel> metricAnalysis = config.componentAnalysisRepository().repository()
				.squadAnalysis(squad.getId(), new Timestamp(date.getTime()));

		Float coverage = AnalysisUtils.metricCoverage(metricAnalysis);

		squad.setCoverage(coverage);
		config.squadRepository().update(squad.getId(), squad);
	}
}
