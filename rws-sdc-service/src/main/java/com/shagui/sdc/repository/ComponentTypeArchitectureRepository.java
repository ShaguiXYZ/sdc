package com.shagui.sdc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.ComponentTypeArchitectureModel;

public interface ComponentTypeArchitectureRepository extends JpaRepository<ComponentTypeArchitectureModel, Integer> {
	public Optional<ComponentTypeArchitectureModel> findByComponentType_IdAndArchitecture_Id(int componentTypeId,
			int architectureId);

}
