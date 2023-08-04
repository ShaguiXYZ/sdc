package com.shagui.sdc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.ComponentTypeArchitectureModel;

public interface ComponentTypeArchitectureRepository extends JpaRepository<ComponentTypeArchitectureModel, Integer> {
	Optional<ComponentTypeArchitectureModel> findByComponentTypeAndArchitectureAndNetworkAndDeploymentTypeAndPlatformAndLanguage(
			String componentType, String architecture, String network, String deployment, String platform,
			String language);
	
	List<ComponentTypeArchitectureModel> findByArchitecture(String architecture);

	List<ComponentTypeArchitectureModel> findByComponentType(String componentType);

	List<ComponentTypeArchitectureModel> findByComponentTypeAndArchitecture(String componentType, String architecture);
}
