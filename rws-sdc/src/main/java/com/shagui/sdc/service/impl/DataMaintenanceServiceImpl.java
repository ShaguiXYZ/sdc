package com.shagui.sdc.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.cmdb.DepartmentInput;
import com.shagui.sdc.api.dto.cmdb.SquadInput;
import com.shagui.sdc.api.dto.ebs.ComponentInput;
import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.ComponentUriModel;
import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.model.pk.ComponentUriPk;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.ComponentTypeArchitectureRepository;
import com.shagui.sdc.repository.ComponentUriRepository;
import com.shagui.sdc.repository.DepartmentRepository;
import com.shagui.sdc.repository.SquadRepository;
import com.shagui.sdc.service.DataMaintenanceService;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

@Service
public class DataMaintenanceServiceImpl implements DataMaintenanceService {
	@Value("classpath:data/departments-squads.json")
	Resource jsonDepartmentsSquads;

	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private ResourceLoader resourceLoader;

	private JpaCommonRepository<ComponentRepository, ComponentModel, Integer> componentRepository;
	private JpaCommonRepository<ComponentUriRepository, ComponentUriModel, ComponentUriPk> componentUriRepository;
	private JpaCommonRepository<SquadRepository, SquadModel, Integer> squadRepository;
	private JpaCommonRepository<DepartmentRepository, DepartmentModel, Integer> departmentRepository;
	private JpaCommonRepository<ComponentTypeArchitectureRepository, ComponentTypeArchitectureModel, Integer> componentTypeArchitectureRepository;

	public DataMaintenanceServiceImpl(ComponentRepository componentRepository,
			ComponentUriRepository componentUriRepository, SquadRepository squadRepository,
			DepartmentRepository departmentRepository,
			ComponentTypeArchitectureRepository componentTypeArchitectureRepository) {
		this.componentRepository = () -> componentRepository;
		this.componentUriRepository = () -> componentUriRepository;
		this.squadRepository = () -> squadRepository;
		this.departmentRepository = () -> departmentRepository;
		this.componentTypeArchitectureRepository = () -> componentTypeArchitectureRepository;
	}
	
	@Transactional
	@Override
	public List<DepartmentDTO> jsonDepartments() {
		try {
			InputStream is = jsonDepartmentsSquads.getInputStream();
			DepartmentInput[] input = mapper.readValue(is, DepartmentInput[].class);
			
			return departmentsData(Arrays.asList(input));
		} catch (IOException e) {
			throw new SdcCustomException("Error reading departments", e);
		}
	}

	@Transactional
	@Override
	public List<DepartmentDTO> jsonDepartments(String path) {
		Resource resource = resourceLoader.getResource("classpath:" + path);
		
		try {
			InputStream is = resource.getInputStream();
			DepartmentInput[] input = mapper.readValue(is, DepartmentInput[].class);
			
			return departmentsData(Arrays.asList(input));
		} catch (IOException e) {
			throw new SdcCustomException("Error reading departments", e);
		}
	}

	@Transactional
	@Override
	public DepartmentDTO departmentData(DepartmentInput data) {
		DepartmentModel model = maintainDepartment(data);

		data.getSquads().forEach(input -> {
			input.setDepartmentId(data.getId());
			model.getSquads().add(maintainSquad(input));
		});

		return Mapper.parse(model);
	}

	@Transactional
	@Override
	public List<DepartmentDTO> departmentsData(List<DepartmentInput> departments) {
		return departments.stream().map(this::departmentData).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public synchronized ComponentDTO componentData(ComponentInput data) {
		SquadModel squadModel = squadRepository.findExistingId(data.getSquad());
		ComponentTypeArchitectureModel componentTypeArchitecture = componentTypeArchitectureRepository.repository()
				.findByComponentTypeAndArchitectureAndNetworkAndDeploymentTypeAndPlatformAndLanguage(
						data.getComponentType(), data.getArchitecture(), data.getNetwork(), data.getDeploymentType(),
						data.getPlatform(), data.getLanguage())
				.orElseThrow(JpaNotFoundException::new);

		ComponentModel component = componentRepository.repository()
				.findBySquad_IdAndName(squadModel.getId(), data.getName()).orElseGet(defaultComponent(data));

		component.setSquad(squadModel);
		component.setComponentTypeArchitecture(componentTypeArchitecture);

		ComponentModel savedComponent = componentRepository.save(component);

		data.getUriNames().forEach(saveUriComponent(savedComponent));

		return Mapper.parse(savedComponent);
	}

	private DepartmentModel maintainDepartment(DepartmentInput data) {
		DepartmentModel departmentModel = departmentRepository.findById(data.getId()).map(data::patch)
				.orElse(data.asModel());

		return departmentRepository.save(departmentModel);
	}

	private SquadModel maintainSquad(SquadInput data) {
		SquadModel squadModel = squadRepository.findById(data.getId()).map(data::patch).orElseGet(data::toModel);
		squadModel = squadRepository.save(squadModel);

		return squadModel;
	}

	private Consumer<String> saveUriComponent(ComponentModel component) {
		return (String uri) -> {
			if (StaticRepository.uris().stream().noneMatch(model -> model.getName().equals(uri))) {
				throw new SdcCustomException(String.format("Not %s uri present!!!", uri));
			}

			ComponentUriModel uriModel = new ComponentUriModel();
			uriModel.setId(new ComponentUriPk(component.getId(), uri));
			uriModel.setComponent(component);

			component.getUris().add(componentUriRepository.save(uriModel));
		};
	}

	private Supplier<ComponentModel> defaultComponent(ComponentInput input) {
		return input::asModel;
	}
}
