package com.shagui.sdc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.ComponetTypeArchitectureMetricPropertiesModel;
import com.shagui.sdc.model.MetricModel;

public interface ComponentTypeArchitectureMetricPropertiesRepository
		extends JpaRepository<ComponetTypeArchitectureMetricPropertiesModel, Integer> {
	Optional<ComponetTypeArchitectureMetricPropertiesModel> findByComponentTypeArchitectureAndMetricAndNameIgnoreCase(
			ComponentTypeArchitectureModel componentTypeArchitecture, MetricModel metric, String name);
}
