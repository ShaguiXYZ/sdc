package com.shagui.analysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.analysis.model.MetricModel;

public interface MetricRepository extends JpaRepository<MetricModel, Integer> {
}
