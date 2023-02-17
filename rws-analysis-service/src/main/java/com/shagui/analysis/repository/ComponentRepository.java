package com.shagui.analysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.analysis.model.ComponentModel;

public interface ComponentRepository extends JpaRepository<ComponentModel, Integer> {
}
