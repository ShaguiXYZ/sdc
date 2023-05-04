package com.shagui.sdc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.pk.ComponentHistoricalCoveragePk;

public interface ComponentHistoricalCoverageRepository
		extends JpaRepository<ComponentHistoricalCoverageModel, ComponentHistoricalCoveragePk> {
	public List<ComponentHistoricalCoverageModel> findById_ComponentId(int componentId);

	public Page<ComponentHistoricalCoverageModel> findById_ComponentId(int componentId, Pageable pageable);
}
