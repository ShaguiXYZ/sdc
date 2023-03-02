package com.shagui.analysis.service.impl;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shagui.analysis.api.dto.MetricAnalysisStateDTO;
import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.api.dto.SquadDTO;
import com.shagui.analysis.model.ComponentAnalysisModel;
import com.shagui.analysis.model.SquadModel;
import com.shagui.analysis.model.pk.ComponentAnalysisPk;
import com.shagui.analysis.repository.ComponentAnalysisRepository;
import com.shagui.analysis.repository.JpaCommonRepository;
import com.shagui.analysis.repository.SquadRepository;
import com.shagui.analysis.service.SquadService;
import com.shagui.analysis.util.AnalysisUtils;
import com.shagui.analysis.util.Mapper;
import com.shagui.analysis.util.collector.SdcCollectors;

@Service
public class SquadServiceImpl implements SquadService {
	private JpaCommonRepository<SquadRepository, SquadModel, Integer> squadRepository;
	private JpaCommonRepository<ComponentAnalysisRepository, ComponentAnalysisModel, ComponentAnalysisPk> componentAnalysisRepository;

	public SquadServiceImpl(SquadRepository squadRepository, ComponentAnalysisRepository componentAnalysisRepository) {
		this.squadRepository = () -> squadRepository;
		this.componentAnalysisRepository = () -> componentAnalysisRepository;
	}

	@Override
	public PageableDTO<SquadDTO> findAll() {
		return squadRepository.findAll().stream().map(Mapper::parse).sorted(Comparator.comparing(SquadDTO::getId))
				.collect(SdcCollectors.toPageable());
	}

	@Override
	public PageableDTO<SquadDTO> findAll(int page) {
		Page<SquadModel> squads = squadRepository.findAll(page);
		return squads.stream().map(Mapper::parse).sorted(Comparator.comparing(SquadDTO::getId))
				.collect(SdcCollectors.toPageable(squads));
	}

	@Override
	public MetricAnalysisStateDTO squadState(int squadId, Date date) {
		List<ComponentAnalysisModel> metricAnalysis = componentAnalysisRepository.repository()
				.squadComponentAnalysis(squadId, new Timestamp(date.getTime()));

		return AnalysisUtils.metricCoverage(metricAnalysis);
	}

}
