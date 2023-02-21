package com.shagui.analysis.service.impl;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shagui.analysis.api.dto.ComponentDTO;
import com.shagui.analysis.api.dto.PaginatedDTO;
import com.shagui.analysis.api.dto.PagingDTO;
import com.shagui.analysis.exception.JpaNotFoundException;
import com.shagui.analysis.model.ComponentModel;
import com.shagui.analysis.model.ComponentTypeArchitectureModel;
import com.shagui.analysis.model.SquadModel;
import com.shagui.analysis.repository.ComponentRepository;
import com.shagui.analysis.repository.ComponentTypeArchitectureRepository;
import com.shagui.analysis.repository.JpaCommonRepository;
import com.shagui.analysis.service.ComponentService;
import com.shagui.analysis.util.Ctes;
import com.shagui.analysis.util.Mapper;

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
	public PaginatedDTO<ComponentDTO> findBySquad(int squadId, int page) {
		Pageable pageable = PageRequest.of(page, Ctes.JPA.ELEMENTS_BY_PAGE);
		Page<ComponentModel> models = componentRepository.repository().findBySquad(new SquadModel(squadId), pageable);

		PaginatedDTO<ComponentDTO> components = new PaginatedDTO<ComponentDTO>(
				new PagingDTO(models.getNumber(), models.getNumberOfElements(), models.getTotalPages()),
				models.getContent().stream().map(Mapper::parse).collect(Collectors.toList()));

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
