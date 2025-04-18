package com.shagui.sdc.service.impl;

import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.repository.DepartmentRepository;
import com.shagui.sdc.service.DepartmentService;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	private JpaCommonRepository<DepartmentRepository, DepartmentModel, Integer> departmentRepository;

	public DepartmentServiceImpl(final DepartmentRepository departmentRepository) {
		this.departmentRepository = () -> departmentRepository;
	}

	@Override
	public DepartmentDTO findById(int departmentId) {
		return Mapper.parse(departmentRepository.findExistingId(departmentId));
	}

	@Override
	public PageData<DepartmentDTO> findAll() {
		return departmentRepository.findAll().stream().map(Mapper::parse)
				.sorted(Comparator.comparing(DepartmentDTO::getName)).collect(SdcCollectors.toPageable());
	}

	@Override
	public PageData<DepartmentDTO> findAll(RequestPageInfo pageInfo) {
		return departmentRepository.findAll(pageInfo).stream().map(Mapper::parse)
				.sorted(Comparator.comparing(DepartmentDTO::getName)).collect(SdcCollectors.toPageable());
	}

	@Override
	public PageData<DepartmentDTO> findByCompany(int companyId) {
		return departmentRepository.repository().findByCompany_Id(companyId).stream()
				.map(Mapper::parse)
				.sorted(Comparator.comparing(DepartmentDTO::getName)).collect(SdcCollectors.toPageable());
	}

	@Override
	public PageData<DepartmentDTO> findByCompany(int companyId, RequestPageInfo pageInfo) {
		return departmentRepository.repository()
				.findByCompany_Id(companyId, pageInfo.getPageable())
				.stream().map(Mapper::parse)
				.sorted(Comparator.comparing(DepartmentDTO::getName)).collect(SdcCollectors.toPageable());
	}
}
