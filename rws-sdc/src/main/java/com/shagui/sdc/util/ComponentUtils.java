package com.shagui.sdc.util;

import java.sql.Timestamp;
import java.util.Date;
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

	public static String propertyValue(ComponentModel component, String key) {
		return propertyValue(component, key, null);
	}

	public static String propertyValue(ComponentModel component, String key, String defaultValue) {
		Optional<ComponentPropertyModel> model = component.getProperties().stream()
				.filter(property -> key.equals(property.getName())).findFirst();
		return model.isPresent() ? model.get().getValue() : defaultValue;
	}
	
	public static List<MetricModel> metricsByType(ComponentModel component, AnalysisType type) {
		return component.getComponentTypeArchitecture().getMetrics().stream()
				.filter(metric -> type.equals(metric.getType())).collect(Collectors.toList());
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
