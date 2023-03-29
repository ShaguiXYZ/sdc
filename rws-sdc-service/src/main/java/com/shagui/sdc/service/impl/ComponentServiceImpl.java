package com.shagui.sdc.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.ComponentTypeArchitectureRepository;
import com.shagui.sdc.repository.JpaCommonRepository;
import com.shagui.sdc.service.ComponentService;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;

@Service
public class ComponentServiceImpl implements ComponentService {
	private JpaCommonRepository<ComponentRepository, ComponentModel, Integer> componentRepository;
	private JpaCommonRepository<ComponentTypeArchitectureRepository, ComponentTypeArchitectureModel, Integer> componentTypeArchitectureRepository;

	public ComponentServiceImpl(ComponentRepository componentRepository,
			ComponentTypeArchitectureRepository componentTypeArchitectureRepository) {
		this.componentRepository = () -> componentRepository;
		this.componentTypeArchitectureRepository = () -> componentTypeArchitectureRepository;
	}

	@Override
	public ComponentDTO create(ComponentDTO component) {
		return Mapper.parse(componentRepository.create(componentModel(component)));
	}

	@Override
	public ComponentDTO update(Integer id, ComponentDTO component) {
		return Mapper.parse(componentRepository.update(id, componentModel(component)));
	}

	@Override
	public PageData<ComponentDTO> findBySquad(int squadId, Integer page) {
		Page<ComponentModel> models = componentRepository.repository().findBySquad(new SquadModel(squadId),
				JpaCommonRepository.getPageable(page));

		PageData<ComponentDTO> components = models.stream().map(Mapper::parse)
				.collect(SdcCollectors.toPageable(models));

		return components;
	}

	private ComponentModel componentModel(ComponentDTO component) {
		ComponentTypeArchitectureModel componentTypeArchitecture = componentTypeArchitecture(
				component.getComponentType().getId(), component.getArchitecture().getId());

		ComponentModel model = Mapper.parse(component);
		model.setComponentTypeArchitecture(componentTypeArchitecture);

		return model;
	}

	private ComponentTypeArchitectureModel componentTypeArchitecture(Integer componentTypeId, Integer architectureId) {
		return componentTypeArchitectureRepository.repository()
				.findByComponentType_IdAndArchitecture_Id(componentTypeId, architectureId)
				.orElseThrow(() -> new JpaNotFoundException());
	}
}
