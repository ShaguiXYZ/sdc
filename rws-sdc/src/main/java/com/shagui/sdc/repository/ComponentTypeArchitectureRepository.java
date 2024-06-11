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

    List<ComponentTypeArchitectureModel> findByComponentTypeAndArchitecture(String componentType,
            String architecture);

    @Query("""
            SELECT cta FROM ComponentTypeArchitectureModel cta WHERE \
            (:componentType IS NULL OR LOWER(cta.componentType) LIKE (:componentType)) AND \
            (:architecture IS NULL OR LOWER(cta.architecture) LIKE (:architecture)) AND \
            (:network IS NULL OR LOWER(cta.network) LIKE (:network)) AND \
            (:deploymentType IS NULL OR LOWER(cta.deploymentType) LIKE (:deploymentType)) AND \
            (:platform IS NULL OR LOWER(cta.platform) LIKE (:platform)) AND \
            (:language IS NULL OR LOWER(cta.language) LIKE (:language)) \
            ORDER BY cta.componentType, cta.architecture, cta.network, cta.deploymentType, cta.architecture, cta.language\
            """)
    List<ComponentTypeArchitectureModel> findBy(String componentType, String architecture, String network,
            String deploymentType, String platform, String language);
}
