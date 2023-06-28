package com.shagui.sdc.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.cmdb.DepartmentInput;
import com.shagui.sdc.api.dto.cmdb.SquadInput;
import com.shagui.sdc.api.dto.ebs.ComponentInput;
import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.model.CompanyModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.ComponentTypeArchitectureRepository;
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
	private JpaCommonRepository<ComponentTypeArchitectureRepository, ComponentTypeArchitectureModel, Integer> componentTypeArchitectureRepository;

	public DataMaintenanceServiceImpl(ComponentRepository componentRepository, SquadRepository squadRepository,
			DepartmentRepository departmentRepository,
			ComponentTypeArchitectureRepository componentTypeArchitectureRepository) {
		this.componentRepository = () -> componentRepository;
		this.squadRepository = () -> squadRepository;
		this.departmentRepository = () -> departmentRepository;
		this.componentTypeArchitectureRepository = () -> componentTypeArchitectureRepository;
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
		SquadModel squadModel = squadRepository.findExistingId(data.getSquad());
		ComponentTypeArchitectureModel componentTypeArchitectureModel = componentTypeArchitectureRepository.repository()
				.findByComponentTypeAndArchitectureAndNetworkAndDeploymentTypeAndPlatformAndLanguage(
						data.getComponentType(), data.getArchitecture(), data.getNetwork(), data.getDeploymentType(),
						data.getPlatform(), data.getLanguage())
				.orElseThrow(JpaNotFoundException::new);

		Optional<ComponentModel> optionalModel = componentRepository.repository()
				.findBySquad_IdAndName(squadModel.getId(), data.getName());

		ComponentModel model;
		if (optionalModel.isPresent()) {
			model = optionalModel.get();
		} else {
			model = new ComponentModel();
			model.setName(data.getName());
		}

		model.setSquad(squadModel);
		model.setComponentTypeArchitecture(componentTypeArchitectureModel);

		return Mapper.parse(componentRepository.save(model));
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
