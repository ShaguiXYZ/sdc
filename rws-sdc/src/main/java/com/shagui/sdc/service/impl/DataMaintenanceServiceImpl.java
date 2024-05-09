package com.shagui.sdc.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
import com.shagui.sdc.api.dto.ebs.ComponentPropertyInput;
import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataMaintenanceServiceImpl implements DataMaintenanceService {
	@Value("classpath:data/departments-squads.json")
	private Resource jsonDepartmentsSquads;

	private final DataMaintenanceService self;
	private final ObjectMapper mapper;
	private final ResourceLoader resourceLoader;
	private JpaCommonRepository<ComponentRepository, ComponentModel, Integer> componentRepository;
	private JpaCommonRepository<ComponentUriRepository, ComponentUriModel, ComponentUriPk> componentUriRepository;
	private JpaCommonRepository<SquadRepository, SquadModel, Integer> squadRepository;
	private JpaCommonRepository<DepartmentRepository, DepartmentModel, Integer> departmentRepository;
	private JpaCommonRepository<ComponentTypeArchitectureRepository, ComponentTypeArchitectureModel, Integer> componentTypeArchitectureRepository;

	public DataMaintenanceServiceImpl(ObjectMapper mapper,
			ResourceLoader resourceLoader,
			final ComponentRepository componentRepository,
			final ComponentUriRepository componentUriRepository, SquadRepository squadRepository,
			final DepartmentRepository departmentRepository,
			final ComponentTypeArchitectureRepository componentTypeArchitectureRepository) {
		this.mapper = mapper;
		this.resourceLoader = resourceLoader;
		this.componentRepository = () -> componentRepository;
		this.componentUriRepository = () -> componentUriRepository;
		this.squadRepository = () -> squadRepository;
		this.departmentRepository = () -> departmentRepository;
		this.componentTypeArchitectureRepository = () -> componentTypeArchitectureRepository;
		this.self = this;
	}

	@Transactional
	@Override
	public List<DepartmentDTO> jsonUpdateDepartments() {
		try {
			InputStream is = jsonDepartmentsSquads.getInputStream();
			DepartmentInput[] input = mapper.readValue(is, DepartmentInput[].class);

			return self.departmentsUpdateData(Arrays.asList(input));
		} catch (IOException e) {
			throw new SdcCustomException("Error reading departments", e);
		}
	}

	@Transactional
	@Override
	public List<DepartmentDTO> jsonUpdateDepartments(String path) {
		Resource resource = resourceLoader.getResource("classpath:" + path);

		try {
			InputStream is = resource.getInputStream();
			DepartmentInput[] input = mapper.readValue(is, DepartmentInput[].class);

			return self.departmentsUpdateData(Arrays.asList(input)); // Call the transactional method via the reference
		} catch (IOException e) {
			throw new SdcCustomException("Error reading departments", e);
		}
	}

	@Transactional
	@Override
	public DepartmentDTO departmentUpdateData(DepartmentInput data) {
		DepartmentModel model = maintainDepartment(data);

		data.getSquads().forEach(input -> {
			input.setDepartmentId(data.getId());
			model.getSquads().add(maintainSquad(input));
		});

		return Mapper.parse(model);
	}

	@Transactional
	@Override
	public List<DepartmentDTO> departmentsUpdateData(List<DepartmentInput> departments) {
		return departments.stream().map(self::departmentUpdateData).toList();
	}

	@Transactional
	@Override
	public synchronized ComponentDTO componentUpdateData(ComponentInput data) {
		SquadModel squadModel = squadRepository.findExistingId(data.getSquad());
		ComponentTypeArchitectureModel componentTypeArchitecture = componentTypeArchitectureRepository.repository()
				.findByComponentTypeAndArchitectureAndNetworkAndDeploymentTypeAndPlatformAndLanguage(
						data.getComponentType(), data.getArchitecture(), data.getNetwork(), data.getDeploymentType(),
						data.getPlatform(), data.getLanguage())
				.orElseThrow(componentTypeArchitectureNotFound(data));

		ComponentModel component = componentRepository.repository()
				.findBySquad_IdAndName(squadModel.getId(), data.getName()).orElseGet(defaultComponent(data));

		maintainComponentProperties(data.getProperties(), component);

		component.setSquad(squadModel);
		component.setComponentTypeArchitecture(componentTypeArchitecture);

		ComponentModel savedComponent = componentRepository.save(component);

		data.getUriNames().forEach(saveUriComponent(savedComponent));

		return Mapper.parse(savedComponent);
	}

	private void maintainComponentProperties(List<ComponentPropertyInput> properties, ComponentModel component) {
		Map<String, ComponentPropertyModel> propertyMap = component.getProperties().stream()
				.collect(Collectors.toMap(ComponentPropertyModel::getName, Function.identity(), (data1, data2) -> {
					log.warn("duplicate key found!");
					return data2;
				}));

		properties.stream().filter(ComponentPropertyInput::isToDelete)
				.forEach(property -> propertyMap.remove(property.getName()));

		properties.forEach(input -> propertyMap.merge(input.getName(),
				new ComponentPropertyModel(component, input.getName(), input.getValue()),
				(oldValue, newValue) -> {
					oldValue.setValue(newValue.getValue());
					return oldValue;
				}));

		component.getProperties().clear();
		component.getProperties().addAll(propertyMap.values());
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
			UriModel staticUriModel = Optional.ofNullable(StaticRepository.uris().get(uri))
					.orElseThrow(() -> new SdcCustomException("Not %s uri present!!!".formatted(uri)));

			List<ComponentUriModel> uris = component.getUris().stream()
					.filter(componentUri -> !componentUri.getId().getUriName().equals(uri))
					.filter(componentUri -> null == staticUriModel.getType()
							|| StaticRepository.uris().values().stream()
									.noneMatch(staticUri -> staticUri.getType().equals(staticUriModel.getType())
											&& staticUri.getName().equals(componentUri.getId().getUriName())))
					.toList();

			ComponentUriModel uriModel = new ComponentUriModel();
			uriModel.setId(new ComponentUriPk(component.getId(), uri));
			uriModel.setComponent(component);

			// uris is a inmutable list, so we need to create a new list with the updated
			List<ComponentUriModel> updatedUris = new ArrayList<>(uris);

			updatedUris.add(componentUriRepository.save(uriModel));
			component.getUris().clear();
			component.getUris().addAll(updatedUris);
		};
	}

	private Supplier<JpaNotFoundException> componentTypeArchitectureNotFound(ComponentInput data) {
		return () -> new JpaNotFoundException(String.format(
				"ComponentType: [%s], Architecture: [%s], DeploymentType: [%s], Language: [%s], Network: [%s], Platform: [%s]  Not found for component '%s'",
				data.getComponentType(), data.getArchitecture(), data.getDeploymentType(),
				data.getLanguage(), data.getNetwork(), data.getPlatform(), data.getName()));
	}

	private Supplier<ComponentModel> defaultComponent(ComponentInput input) {
		return input::asModel;
	}
}
