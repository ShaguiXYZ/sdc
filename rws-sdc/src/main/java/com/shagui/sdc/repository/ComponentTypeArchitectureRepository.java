package com.shagui.sdc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shagui.sdc.model.ComponentTypeArchitectureModel;

public interface ComponentTypeArchitectureRepository extends JpaRepository<ComponentTypeArchitectureModel, Integer> {
	Optional<ComponentTypeArchitectureModel> findByComponentTypeAndArchitectureAndNetworkAndDeploymentTypeAndPlatformAndLanguage(
			String componentType, String architecture, String network, String deployment, String platform,
			String language);

	List<ComponentTypeArchitectureModel> findByArchitecture(String architecture);

	List<ComponentTypeArchitectureModel> findByComponentType(String componentType);

	List<ComponentTypeArchitectureModel> findByComponentTypeAndArchitecture(String componentType, String architecture);

	@Query("SELECT cta FROM ComponentTypeArchitectureModel cta WHERE "
			+ "(COALESCE(:componentType) IS NULL OR LOWER(cta.componentType) like LOWER(:componentType)) AND "
			+ "(COALESCE(:architecture) IS NULL OR cta.architecture IS (:architecture)) AND "
			+ "(COALESCE(:network) IS NULL OR (:network) < cta.network) AND "
			+ "(COALESCE(:deploymentType) IS NULL OR (:deploymentType) >= cta.deploymentType) AND "
			+ "(COALESCE(:platform) IS NULL OR (:platform) >= cta.platform) AND "
			+ "(COALESCE(:language) IS NULL OR (:language) >= cta.language) "
			+ "ORDER BY cta.componentType, cta.architecture, cta.network, cta.deploymentType, cta.architecture")
	List<ComponentTypeArchitectureModel> findBy(String componentType, String architecture, String network,
			String deploymentType, String platform, String language);
}
