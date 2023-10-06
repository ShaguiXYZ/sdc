package com.shagui.sdc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.ComponentPropertyModel;

public interface ComponentPropertyRepository extends JpaRepository<ComponentPropertyModel, Integer> {
	Optional<ComponentPropertyModel> findByComponent_IdAndName(Integer componentId, String name);
}
