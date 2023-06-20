package com.shagui.sdc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.ComponentTypeArchitectureModel;

public interface ComponentTypeArchitectureRepository extends JpaRepository<ComponentTypeArchitectureModel, Integer> {
	public Optional<ComponentTypeArchitectureModel> findByComponentTypeAndArchitectureAndNetworkAndDeploymentTypeAndPlatformAndLanguage(
			String componentType, String architecture, String network, String deployment, String platform,
			String language);
}
