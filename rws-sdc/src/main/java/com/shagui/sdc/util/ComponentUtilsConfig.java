package com.shagui.sdc.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.model.pk.ComponentAnalysisPk;
import com.shagui.sdc.model.pk.ComponentHistoricalCoveragePk;
import com.shagui.sdc.repository.ComponentAnalysisRepository;
import com.shagui.sdc.repository.ComponentHistoricalCoverageRepository;
import com.shagui.sdc.repository.ComponentPropertyRepository;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.JpaCommonRepository;
import com.shagui.sdc.repository.SquadRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ComponentUtilsConfig {
	@Autowired
	private ComponentRepository componentRepository;
	
	@Autowired
	private ComponentAnalysisRepository componentAnalysisRepository;

	@Autowired
	private ComponentPropertyRepository componentPropertyRepository;

	@Autowired
	private ComponentHistoricalCoverageRepository historicalCoverageComponentRepository;
	
	@Autowired
	private SquadRepository squadRepository;

	public JpaCommonRepository<ComponentRepository, ComponentModel, Integer> componentRepository() {
		return () -> componentRepository;
	}

	public JpaCommonRepository<ComponentAnalysisRepository, ComponentAnalysisModel, ComponentAnalysisPk> componentAnalysisRepository() {
		return () -> componentAnalysisRepository;
	}

	public JpaCommonRepository<ComponentPropertyRepository, ComponentPropertyModel, Integer> componentPropertyRepository() {
		return () -> componentPropertyRepository;
	}

	public JpaCommonRepository<ComponentHistoricalCoverageRepository, ComponentHistoricalCoverageModel, ComponentHistoricalCoveragePk> historicalCoverageComponentRepository() {
		return () -> historicalCoverageComponentRepository;
	}

	public JpaCommonRepository<SquadRepository, SquadModel, Integer> squadRepository() {
		return () -> squadRepository;
	}

	@PostConstruct
	public void init() {
		ComponentUtils.setConfig(this);
	}
}
