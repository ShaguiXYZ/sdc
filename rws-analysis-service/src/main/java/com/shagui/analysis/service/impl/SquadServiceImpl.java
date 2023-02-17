package com.shagui.analysis.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.model.SquadModel;
import com.shagui.analysis.repository.JpaCommonRepository;
import com.shagui.analysis.repository.SquadRepository;
import com.shagui.analysis.service.SquadService;
import com.shagui.analysis.util.Mapper;

@Service
public class SquadServiceImpl implements SquadService {
	private JpaCommonRepository<SquadRepository, SquadModel, Integer> squadRepository;
	
	public SquadServiceImpl(SquadRepository squadRepository) {
		this.squadRepository = () -> squadRepository;
	}

	@Override
	public List<ComponentDTO> squadComponents(int id) {
		return squadRepository.findById(id).getComponents().stream().map(Mapper::parse).collect(Collectors.toList());
	}

}
