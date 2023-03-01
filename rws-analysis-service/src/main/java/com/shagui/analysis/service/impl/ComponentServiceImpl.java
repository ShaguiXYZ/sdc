package com.shagui.analysis.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.PageableDTO;
import com.shagui.analysis.core.exception.JpaNotFoundException;
import com.shagui.analysis.model.ComponentModel;
import com.shagui.analysis.model.ComponentTypeArchitectureModel;
import com.shagui.analysis.model.SquadModel;
import com.shagui.analysis.repository.ComponentRepository;
import com.shagui.analysis.repository.ComponentTypeArchitectureRepository;
import com.shagui.analysis.repository.JpaCommonRepository;
import com.shagui.analysis.service.ComponentService;
import com.shagui.analysis.util.Mapper;
import com.shagui.analysis.util.collector.SdcCollectors;

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
	public PageableDTO<ComponentDTO> findBySquad(int squadId, int page) {
		Page<ComponentModel> models = componentRepository.repository().findBySquad(new SquadModel(squadId),
				JpaCommonRepository.getPageable(page));

		PageableDTO<ComponentDTO> components = models.stream().map(Mapper::parse)
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