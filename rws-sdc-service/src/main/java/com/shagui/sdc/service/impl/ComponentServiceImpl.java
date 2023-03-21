package com.shagui.sdc.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.ComponentHistoricalCoverageDTO;
import com.shagui.sdc.api.dto.MetricAnalysisStateDTO;
import com.shagui.sdc.api.dto.PageableDTO;
import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.model.pk.ComponentAnalysisPk;
import com.shagui.sdc.model.pk.ComponentHistoricalCoveragePk;
import com.shagui.sdc.repository.ComponentAnalysisRepository;
import com.shagui.sdc.repository.ComponentHistoricalCoverageRepository;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.ComponentTypeArchitectureRepository;
import com.shagui.sdc.repository.JpaCommonRepository;
import com.shagui.sdc.service.ComponentService;
import com.shagui.sdc.util.AnalysisUtils;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;

@Service
public class ComponentServiceImpl implements ComponentService {
	private JpaCommonRepository<ComponentRepository, ComponentModel, Integer> componentRepository;
	private JpaCommonRepository<ComponentTypeArchitectureRepository, ComponentTypeArchitectureModel, Integer> componentTypeArchitectureRepository;
	private JpaCommonRepository<ComponentAnalysisRepository, ComponentAnalysisModel, ComponentAnalysisPk> componentAnalysisRepository;
	private JpaCommonRepository<ComponentHistoricalCoverageRepository, ComponentHistoricalCoverageModel, ComponentHistoricalCoveragePk> historicalCoverageComponentRepository;

	public ComponentServiceImpl(ComponentRepository componentRepository,
			ComponentTypeArchitectureRepository componentTypeArchitectureRepository,
			ComponentAnalysisRepository componentAnalysisRepository,
			ComponentHistoricalCoverageRepository historicalCoverageComponentRepository) {
		this.componentRepository = () -> componentRepository;
		this.componentTypeArchitectureRepository = () -> componentTypeArchitectureRepository;
		this.componentAnalysisRepository = () -> componentAnalysisRepository;
		this.historicalCoverageComponentRepository = () -> historicalCoverageComponentRepository;
	}

	@Override
	public MetricAnalysisStateDTO componentState(int componentId, Date date) {
		List<ComponentAnalysisModel> metricAnalysis = componentAnalysisRepository.repository()
				.componentAnalysis(componentId, new Timestamp(date.getTime()));

		return AnalysisUtils.metricCoverage(metricAnalysis);
	}

	@Override
	public ComponentDTO create(ComponentDTO component) {
		return Mapper.parse(componentRepository.create(componentModel(component)));
	}

	@Override
	public ComponentDTO update(Integer id, ComponentDTO component) {
		return Mapper.parse(componentRepository.update(id, componentModel(component)));
	}

	@Override
	public PageableDTO<ComponentDTO> findBySquad(int squadId, Integer page) {
		Page<ComponentModel> models = componentRepository.repository().findBySquad(new SquadModel(squadId),
				JpaCommonRepository.getPageable(page));

		PageableDTO<ComponentDTO> components = models.stream().map(Mapper::parse)
				.collect(SdcCollectors.toPageable(models));

		return components;
	}

	@Override
	public PageableDTO<ComponentHistoricalCoverageDTO> historicalCoverage(int componentId) {
		return historicalCoverageComponentRepository.repository().findById_ComponentId(componentId).stream()
				.map(Mapper::parse).collect(SdcCollectors.toPageable());
	}

	private ComponentModel componentModel(ComponentDTO component) {
		ComponentTypeArchitectureModel componentTypeArchitecture = componentTypeArchitecture(
				component.getComponentType().getId(), component.getArchitecture().getId());

		ComponentModel model = Mapper.parse(component);
		model.setComponentTypeArchitecture(componentTypeArchitecture);

		return model;
	}

	private ComponentTypeArchitectureModel componentTypeArchitecture(Integer componentTypeId, Integer architectureId) {
		return componentTypeArchitectureRepository.repository()
				.findByComponentType_IdAndArchitecture_Id(componentTypeId, architectureId)
				.orElseThrow(() -> new JpaNotFoundException());
	}
}
