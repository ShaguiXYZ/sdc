package com.shagui.sdc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.model.MetricModel;

public interface MetricRepository extends JpaRepository<MetricModel, Integer> {
	Optional<MetricModel> findByNameIgnoreCaseAndType(String name, AnalysisType type);
}
