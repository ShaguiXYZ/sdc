package com.shagui.sdc.util;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shagui.sdc.core.configuration.DictionaryConfig;
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
import com.shagui.sdc.repository.SquadRepository;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ComponentUtilsConfig {
	@Autowired
	private final DictionaryConfig tokens;

	@Autowired
	private final ComponentRepository componentRepository;

	@Autowired
	private final ComponentAnalysisRepository componentAnalysisRepository;

	@Autowired
	private final ComponentPropertyRepository componentPropertyRepository;

	@Autowired
	private final ComponentHistoricalCoverageRepository historicalCoverageComponentRepository;

	@Autowired
	private final SquadRepository squadRepository;

	public Map<String, String> tokens() {
		return tokens.secret();
	}

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
