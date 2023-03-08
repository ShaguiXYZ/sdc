package com.shagui.analysis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.analysis.model.ComponentHistoricalCoverageModel;
import com.shagui.analysis.model.pk.ComponentHistoricalCoveragePk;

public interface ComponentHistoricalCoverageRepository extends JpaRepository<ComponentHistoricalCoverageModel, ComponentHistoricalCoveragePk> {
	public List<ComponentHistoricalCoverageModel> findById_ComponentId(int componentId);

}
