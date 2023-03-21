package com.shagui.sdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.ComponentPropertyModel;

public interface ComponentPropertyRepository extends JpaRepository<ComponentPropertyModel, Integer> {
}
