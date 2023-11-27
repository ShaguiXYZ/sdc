package com.shagui.sdc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.ComponentPropertyModel;

public interface ComponentPropertyRepository extends JpaRepository<ComponentPropertyModel, Integer> {
	List<ComponentPropertyModel> findByComponent_Id(Integer componentId);

	Optional<ComponentPropertyModel> findByComponent_IdAndName(Integer componentId, String name);

	long countByComponent_Id(Integer componentId);
}
