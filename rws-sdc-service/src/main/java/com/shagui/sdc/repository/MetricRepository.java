package com.shagui.sdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.MetricModel;

public interface MetricRepository extends JpaRepository<MetricModel, Integer> {
}
