package com.shagui.sdc.util;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.shagui.sdc.core.configuration.DictionaryConfig;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
import com.shagui.sdc.model.ComponentTagModel;
import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.model.TagModel;
import com.shagui.sdc.model.pk.ComponentAnalysisPk;
import com.shagui.sdc.model.pk.ComponentHistoricalCoveragePk;
import com.shagui.sdc.model.pk.ComponentTagPk;
import com.shagui.sdc.repository.ComponentAnalysisRepository;
import com.shagui.sdc.repository.ComponentHistoricalCoverageRepository;
import com.shagui.sdc.repository.ComponentPropertyRepository;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.ComponentTagRepository;
import com.shagui.sdc.repository.DepartmentRepository;
import com.shagui.sdc.repository.SquadRepository;
import com.shagui.sdc.repository.TagRepository;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ComponentUtilsConfig {
	private final DictionaryConfig tokens;
	private final ComponentRepository componentRepository;
	private final ComponentAnalysisRepository componentAnalysisRepository;
	private final ComponentHistoricalCoverageRepository componentHistoricalCoverageRepository;
	private final ComponentPropertyRepository componentPropertyRepository;
	private final ComponentTagRepository componentTagRepository;
	private final DepartmentRepository departmentRepository;
	private final SquadRepository squadRepository;
	private final TagRepository tagRepository;

	public Map<String, String> tokens() {
		return tokens.secret();
	}

	public JpaCommonRepository<ComponentRepository, ComponentModel, Integer> componentRepository() {
		return () -> componentRepository;
	}

	public JpaCommonRepository<ComponentAnalysisRepository, ComponentAnalysisModel, ComponentAnalysisPk> componentAnalysisRepository() {
		return () -> componentAnalysisRepository;
	}

	public JpaCommonRepository<ComponentHistoricalCoverageRepository, ComponentHistoricalCoverageModel, ComponentHistoricalCoveragePk> historicalCoverageComponentRepository() {
		return () -> componentHistoricalCoverageRepository;
	}

	public JpaCommonRepository<ComponentPropertyRepository, ComponentPropertyModel, Integer> componentPropertyRepository() {
		return () -> componentPropertyRepository;
	}

	public JpaCommonRepository<ComponentTagRepository, ComponentTagModel, ComponentTagPk> componentTagRepository() {
		return () -> componentTagRepository;
	}

	public JpaCommonRepository<DepartmentRepository, DepartmentModel, Integer> departmentRepository() {
		return () -> departmentRepository;
	}

	public JpaCommonRepository<SquadRepository, SquadModel, Integer> squadRepository() {
		return () -> squadRepository;
	}

	public JpaCommonRepository<TagRepository, TagModel, Integer> tagRepository() {
		return () -> tagRepository;
	}

	@PostConstruct
	public void init() {
		ComponentUtils.setConfig(this);
	}
}
