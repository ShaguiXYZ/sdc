package com.shagui.sdc.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.cmdb.ComponentInput;
import com.shagui.sdc.api.dto.cmdb.DepartmentInput;
import com.shagui.sdc.api.dto.cmdb.SquadInput;
import com.shagui.sdc.model.CompanyModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.DepartmentRepository;
import com.shagui.sdc.repository.SquadRepository;
import com.shagui.sdc.service.DataMaintenanceService;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

@Service
public class DataMaintenanceServiceImpl implements DataMaintenanceService {

	private JpaCommonRepository<ComponentRepository, ComponentModel, Integer> componentRepository;
	private JpaCommonRepository<SquadRepository, SquadModel, Integer> squadRepository;
	private JpaCommonRepository<DepartmentRepository, DepartmentModel, Integer> departmentRepository;

	public DataMaintenanceServiceImpl(ComponentRepository componentRepository, SquadRepository squadRepository,
			DepartmentRepository departmentRepository) {
		this.componentRepository = () -> componentRepository;
		this.squadRepository = () -> squadRepository;
		this.departmentRepository = () -> departmentRepository;
	}

	@Transactional
	@Override
	public DepartmentDTO departmentData(DepartmentInput data) {
		DepartmentModel model = maintainDepartment.apply(data);

		data.getSquads().forEach(input -> {
			input.setDepartmentId(data.getId());
			maintainSquad.apply(input);
		});

		return Mapper.parse(model);
	}

	@Transactional
	@Override
	public List<DepartmentDTO> departmentsData(List<DepartmentInput> data) {
		List<DepartmentModel> models = data.stream().map(maintainDepartment).collect(Collectors.toList());

		List<SquadInput> squadsInput = data.stream().map(d -> d.getSquads().stream().map(s -> {
			s.setDepartmentId(d.getId());
			return s;
		}).collect(Collectors.toList())).flatMap(List::stream).collect(Collectors.toList());

		squadsInput.forEach(input -> maintainSquad.apply(input));

		return models.stream().map(Mapper::parse).collect(Collectors.toList());
	}

	@Override
	public ComponentDTO componentData(ComponentInput data) {
		// TODO Auto-generated method stub
		return null;
	}

	private Function<DepartmentInput, DepartmentModel> maintainDepartment = (DepartmentInput data) -> {
		Optional<DepartmentModel> optionalDepartment = departmentRepository.findById(data.getId());
		DepartmentModel departmentModel = optionalDepartment.isPresent() ? optionalDepartment.get()
				: new DepartmentModel(data.getId());

		departmentModel.setName(data.getName());
		departmentModel.setCompany(new CompanyModel(data.getCia()));
		departmentModel = departmentRepository.save(departmentModel);

		return departmentModel;
	};

	private Function<SquadInput, SquadModel> maintainSquad = (SquadInput data) -> {
		Optional<SquadModel> optionalSquad = squadRepository.findById(data.getId());
		SquadModel squadModel = optionalSquad.isPresent() ? optionalSquad.get() : new SquadModel(data.getId());

		squadModel.setName(data.getName());
		squadModel.setDepartment(new DepartmentModel(data.getDepartmentId()));
		squadModel = squadRepository.save(squadModel);

		return squadModel;
	};

}
