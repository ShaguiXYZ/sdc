package com.shagui.sdc.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.HistoricalCoverageDTO;
import com.shagui.sdc.api.dto.TimeCoverageDTO;
import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.pk.ComponentHistoricalCoveragePk;
import com.shagui.sdc.repository.ComponentHistoricalCoverageRepository;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.service.ComponentHistoricalCoverageService;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

@Service
public class ComponentHistoricalCoverageServiceImpl implements ComponentHistoricalCoverageService {
	private JpaCommonRepository<ComponentHistoricalCoverageRepository, ComponentHistoricalCoverageModel, ComponentHistoricalCoveragePk> componentHistoricalCoverageRepository;
	private JpaCommonRepository<ComponentRepository, ComponentModel, Integer> componentRepository;

	public ComponentHistoricalCoverageServiceImpl(final ComponentRepository componentRepository,
			final ComponentHistoricalCoverageRepository componentHistoricalCoverageRepository) {
		this.componentRepository = () -> componentRepository;
		this.componentHistoricalCoverageRepository = () -> componentHistoricalCoverageRepository;
	}

	@Override
	public HistoricalCoverageDTO<ComponentDTO> historicalCoverage(int componentId) {
		ComponentModel component = componentRepository.findExistingId(componentId);
		List<ComponentHistoricalCoverageModel> historical = componentHistoricalCoverageRepository.repository()
				.findById_ComponentIdOrderById_AnalysisDateAsc(component.getId());

		PageData<TimeCoverageDTO> timeCoverage = historical.stream().map(Mapper::parse)
				.collect(SdcCollectors.toPageable());
		return new HistoricalCoverageDTO<>(Mapper.parse(component), timeCoverage);
	}

	@Override
	public HistoricalCoverageDTO<ComponentDTO> historicalCoverage(int componentId, RequestPageInfo pageInfo) {
		ComponentModel component = componentRepository.findExistingId(componentId);
		Page<ComponentHistoricalCoverageModel> historical = componentHistoricalCoverageRepository.repository()
				.findById_ComponentIdOrderById_AnalysisDateAsc(component.getId(), pageInfo.getPageable());

		PageData<TimeCoverageDTO> timeCoverage = historical.stream().map(Mapper::parse)
				.collect(SdcCollectors.toPageable(historical));
		return new HistoricalCoverageDTO<>(Mapper.parse(component), timeCoverage);
	}

}
