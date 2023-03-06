package com.shagui.analysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.analysis.model.ComponentPropertyModel;

public interface ComponentPropertyRepository extends JpaRepository<ComponentPropertyModel, Integer> {
}
