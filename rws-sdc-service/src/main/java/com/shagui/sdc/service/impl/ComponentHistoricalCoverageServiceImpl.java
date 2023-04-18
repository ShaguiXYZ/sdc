package com.shagui.sdc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.HistoricalCoverageDTO;
import com.shagui.sdc.api.dto.TimeCoverageDTO;
import com.shagui.sdc.model.ComponentHistoricalCoverageModel;
import com.shagui.sdc.model.pk.ComponentHistoricalCoveragePk;
import com.shagui.sdc.repository.ComponentHistoricalCoverageRepository;
import com.shagui.sdc.repository.JpaCommonRepository;
import com.shagui.sdc.service.ComponentHistoricalCoverageService;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;

@Service
public class ComponentHistoricalCoverageServiceImpl implements ComponentHistoricalCoverageService {

	private JpaCommonRepository<ComponentHistoricalCoverageRepository, ComponentHistoricalCoverageModel, ComponentHistoricalCoveragePk> componentHistoricalCoverageRepository;

	public ComponentHistoricalCoverageServiceImpl(
			ComponentHistoricalCoverageRepository componentHistoricalCoverageRepository) {
		this.componentHistoricalCoverageRepository = () -> componentHistoricalCoverageRepository;
	}

	@Override
	public HistoricalCoverageDTO<ComponentDTO> historicalCoverage(int componentId) {
		List<ComponentHistoricalCoverageModel> historical = componentHistoricalCoverageRepository.repository()
				.findById_ComponentId(componentId);

		Optional<ComponentHistoricalCoverageModel> first = historical.stream().findFirst();

		if (first.isPresent()) {
			PageData<TimeCoverageDTO> timeCoverage = historical.stream().map(Mapper::parse)
					.collect(SdcCollectors.toPageable());
			return new HistoricalCoverageDTO<ComponentDTO>(Mapper.parse(first.get().getComponent()), timeCoverage);
		}

		return null;
	}

	@Override
	public HistoricalCoverageDTO<ComponentDTO> historicalCoverage(int componentId, RequestPageInfo pageInfo) {
		Page<ComponentHistoricalCoverageModel> historical = componentHistoricalCoverageRepository.repository()
				.findById_ComponentId(componentId, pageInfo.getPageable());

		Optional<ComponentHistoricalCoverageModel> first = historical.stream().findFirst();

		if (first.isPresent()) {
			PageData<TimeCoverageDTO> timeCoverage = historical.stream().map(Mapper::parse)
					.collect(SdcCollectors.toPageable(historical));
			return new HistoricalCoverageDTO<ComponentDTO>(Mapper.parse(first.get().getComponent()), timeCoverage);
		}

		return null;
	}

}
