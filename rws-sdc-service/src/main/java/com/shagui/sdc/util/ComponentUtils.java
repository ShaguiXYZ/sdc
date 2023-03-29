package com.shagui.sdc.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
import com.shagui.sdc.model.SquadModel;

public class ComponentUtils {
	private static ComponentUtilsConfig config;

	private ComponentUtils() {
	}

	public static void setConfig(ComponentUtilsConfig config) {
		ComponentUtils.config = config;
	}

	@Transactional
	public static List<ComponentAnalysisModel> addOrUpdateComponentPorperties(ComponentModel component) {
		Date date = new Date();

		List<ComponentAnalysisModel> metricAnalysis = config.componentAnalysisRepository().repository()
				.componentAnalysis(component.getId(), new Timestamp(date.getTime()));

		// Analysis Properties
		Float coverage = AnalysisUtils.metricCoverage(metricAnalysis);
		
		addOrUpdatePropertyValue(component, Ctes.COMPONENT_PROPERTIES.COMPONENT_COVERAGE, Float.toString(coverage));
		addOrUpdatePropertyValue(component, Ctes.COMPONENT_PROPERTIES.COMPONENT_ANALYSIS_DATE,
				Long.toString(date.getTime()));

		saveHistoricalCoverage(component, date, coverage);
		updateSquadPorperties(component);

		return metricAnalysis;
	}

	public static String propertyValue(ComponentModel component, String key) {
		return propertyValue(component, key, null);
	}

	public static String propertyValue(ComponentModel component, String key, String defaultValue) {
		Optional<ComponentPropertyModel> model = component.getProperties().stream()
				.filter(property -> key.equals(property.getName())).findFirst();
		return model.isPresent() ? model.get().getValue() : defaultValue;
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

	private static void updateSquadPorperties(ComponentModel component) {
		Date date = new Date();
		SquadModel squad = component.getSquad();

		List<ComponentAnalysisModel> metricAnalysis = config.componentAnalysisRepository().repository()
				.squadComponentAnalysis(squad.getId(), new Timestamp(date.getTime()));

		// Analysis Properties
		Float coverage = AnalysisUtils.metricCoverage(metricAnalysis);

		squad.setCoverage(coverage);
		config.squadRepository().update(squad.getId(), squad);
	}
}
