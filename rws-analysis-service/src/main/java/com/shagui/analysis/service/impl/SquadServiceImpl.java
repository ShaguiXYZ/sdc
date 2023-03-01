package com.shagui.analysis.service.impl;

import java.util.Comparator;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.api.dto.SquadDTO;
import com.shagui.analysis.model.SquadModel;
import com.shagui.analysis.repository.JpaCommonRepository;
import com.shagui.analysis.repository.SquadRepository;
import com.shagui.analysis.service.SquadService;
import com.shagui.analysis.util.Mapper;
import com.shagui.analysis.util.collector.SdcCollectors;

@Service
public class SquadServiceImpl implements SquadService {
	private JpaCommonRepository<SquadRepository, SquadModel, Integer> squadRepository;

	public SquadServiceImpl(SquadRepository squadRepository) {
		this.squadRepository = () -> squadRepository;
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

}
