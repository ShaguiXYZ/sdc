package com.shagui.sdc.service.impl;

import java.util.Comparator;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.SquadDTO;
import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.repository.SquadRepository;
import com.shagui.sdc.service.SquadService;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

@Service
public class SquadServiceImpl implements SquadService {
	private JpaCommonRepository<SquadRepository, SquadModel, Integer> squadRepository;

	public SquadServiceImpl(SquadRepository squadRepository) {
		this.squadRepository = () -> squadRepository;
	}

	@Override
	public SquadDTO findById(int squadId) {
		return Mapper.parse(squadRepository.findExistingId(squadId));
	}

	@Override
	public PageData<SquadDTO> findAll() {
		return squadRepository.findAll().stream().map(Mapper::parse).sorted(Comparator.comparing(SquadDTO::getName))
				.collect(SdcCollectors.toPageable());
	}

	@Override
	public PageData<SquadDTO> findAll(RequestPageInfo pageInfo) {
		return squadRepository.findAll(pageInfo).stream().map(Mapper::parse)
				.sorted(Comparator.comparing(SquadDTO::getName)).collect(SdcCollectors.toPageable());
	}

	@Override
	public PageData<SquadDTO> findByDepartment(int departmentId) {
		return squadRepository.repository().findByDepartment(new DepartmentModel(departmentId)).stream()
				.map(Mapper::parse).sorted(Comparator.comparing(SquadDTO::getName)).collect(SdcCollectors.toPageable());
	}

	@Override
	public PageData<SquadDTO> findByDepartment(int departmentId, RequestPageInfo pageInfo) {
		Page<SquadModel> squads = squadRepository.repository().findByDepartment(new DepartmentModel(departmentId),
				pageInfo.getPageable());

		return squads.stream().map(Mapper::parse).sorted(Comparator.comparing(SquadDTO::getName))
				.collect(SdcCollectors.toPageable(squads));
	}
}
