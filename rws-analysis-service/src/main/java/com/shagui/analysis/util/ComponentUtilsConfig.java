package com.shagui.analysis.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shagui.analysis.model.ComponentAnalysisModel;
import com.shagui.analysis.model.ComponentModel;
import com.shagui.analysis.model.ComponentPropertyModel;
import com.shagui.analysis.model.pk.ComponentAnalysisPk;
import com.shagui.analysis.repository.ComponentAnalysisRepository;
import com.shagui.analysis.repository.ComponentPropertyRepository;
import com.shagui.analysis.repository.ComponentRepository;
import com.shagui.analysis.repository.JpaCommonRepository;

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

	public JpaCommonRepository<ComponentRepository, ComponentModel, Integer> componentRepository() {
		return () -> componentRepository;
	}

	public JpaCommonRepository<ComponentAnalysisRepository, ComponentAnalysisModel, ComponentAnalysisPk> componentAnalysisRepository() {
		return () -> componentAnalysisRepository;
	}

	public JpaCommonRepository<ComponentPropertyRepository, ComponentPropertyModel, Integer> componentPropertyRepository() {
		return () -> componentPropertyRepository;
	}

	@PostConstruct
	public void init() {
		ComponentUtils.setConfig(this);
	}
}
