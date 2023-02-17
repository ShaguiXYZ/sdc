package com.shagui.analysis.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shagui.analysis.model.MetricValuesModel;

public interface MetricValueRepository extends JpaRepository<MetricValuesModel, Integer> {
	@Query("SELECT mv FROM MetricValuesModel mv "
			+ "WHERE mv.metric.id = :metricId AND mv.componentTypeArchitecture.id = :componentTypeArchitectureId AND mv.metricValueDate <= :date "
			+ "ORDER BY mv.metricValueDate DESC")
	List<MetricValuesModel> metricValueByDate(int metricId, int componentTypeArchitectureId, Date date);
}
