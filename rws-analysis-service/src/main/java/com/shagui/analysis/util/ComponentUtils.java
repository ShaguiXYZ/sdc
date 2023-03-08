package com.shagui.analysis.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.shagui.analysis.model.ComponentAnalysisModel;
import com.shagui.analysis.model.ComponentModel;
import com.shagui.analysis.model.ComponentPropertyModel;
import com.shagui.analysis.model.ComponentHistoricalCoverageModel;

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
		Float coverage = AnalysisUtils.metricCoverage(metricAnalysis).getCoverage();
		addOrUpdatePropertyValue(component, Ctes.COMPONENT_PROPERTIES.COMPONENT_COVERAGE, Float.toString(coverage));
		addOrUpdatePropertyValue(component, Ctes.COMPONENT_PROPERTIES.COMPONENT_ANALYSIS_DATE,
				Long.toString(date.getTime()));
		
		saveHistoricalCoverageComponent(component, date, coverage);

		return metricAnalysis;
	}

	public static void addOrUpdatePropertyValue(ComponentModel component, String propertyName, String propertyValue) {
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

	public static String propertyValue(ComponentModel component, String key) {
		return propertyValue(component, key, null);
	}

	public static String propertyValue(ComponentModel component, String key, String defaultValue) {
		Optional<ComponentPropertyModel> model = component.getProperties().stream()
				.filter(property -> key.equals(property.getName())).findFirst();
		return model.isPresent() ? model.get().getValue() : defaultValue;
	}

	private static void saveHistoricalCoverageComponent(ComponentModel component, Date date, Float coverage) {
		config.historicalCoverageComponentRepository()
				.create(new ComponentHistoricalCoverageModel(component, date, coverage));
	}
}
