package com.shagui.sdc.service.impl;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.Range;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.ComponentTypeArchitectureDTO;
import com.shagui.sdc.api.dto.MetricDTO;
import com.shagui.sdc.core.exception.ExceptionCodes;
import com.shagui.sdc.core.exception.JpaNotFoundException;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.ComponentTypeArchitectureRepository;
import com.shagui.sdc.service.ComponentService;
import com.shagui.sdc.util.ComponentUtils;
import com.shagui.sdc.util.Mapper;
import com.shagui.sdc.util.collector.SdcCollectors;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

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
	public ComponentDTO findBy(int componentId) {
		return Mapper.parse(componentRepository.findExistingId(componentId));
	}

	@Override
	public ComponentDTO findBy(int squadId, String name) {
		return Mapper.parse(componentRepository.repository().findBySquad_IdAndName(squadId, name)
				.orElseThrow(() -> new JpaNotFoundException(ExceptionCodes.NOT_FOUND_COMPONENT,
						String.format("no result found for squad %s and component name %s", squadId, name))));
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
	public PageData<ComponentDTO> squadComponents(int squadId) {
		return componentRepository.repository().findBySquad_Id(squadId, Sort.by("coverage").and(Sort.by("name")))
				.stream().map(Mapper::parse).collect(SdcCollectors.toPageable());
	}

	@Override
	public PageData<ComponentDTO> squadComponents(int squadId, RequestPageInfo pageInfo) {
		Page<ComponentModel> data = componentRepository.repository().findBySquad_Id(squadId,
				pageInfo.getPageable(Sort.by("coverage").and(Sort.by("name"))));

		return data.stream().map(Mapper::parse).collect(SdcCollectors.toPageable(data));
	}

	@Override
	public PageData<ComponentDTO> filter(String name, Integer squadId, Range range) {
		return componentRepository.repository()
				.filter(name, squadId == null ? null : new SquadModel(squadId), range.getMin(), range.getMax()).stream()
				.map(Mapper::parse).collect(SdcCollectors.toPageable());
	}

	@Override
	public PageData<ComponentDTO> filter(String name, Integer squadId, Range range, RequestPageInfo pageInfo) {
		Page<ComponentModel> models = componentRepository.repository().filter(name,
				squadId == null ? null : new SquadModel(squadId), range.getMin(), range.getMax(),
				pageInfo.getPageable());

		return models.stream().map(Mapper::parse).collect(SdcCollectors.toPageable(models));
	}

	@Override
	public PageData<MetricDTO> componentMetrics(int componentId) {
		ComponentModel model = componentRepository.findExistingId(componentId);

		return model.getComponentTypeArchitecture().getMetrics().stream().map(Mapper::parse)
				.collect(SdcCollectors.toPageable());
	}

	@Override
	public Map<String, String> dictionary(int componentId) {
		return ComponentUtils.dictionaryOf(componentRepository.findExistingId(componentId));
	}

	private ComponentModel componentModel(ComponentDTO component) {
		ComponentTypeArchitectureModel componentTypeArchitecture = componentTypeArchitecture(
				component.getComponentTypeArchitecture());
		ComponentModel model = Mapper.parse(component);
		model.setComponentTypeArchitecture(componentTypeArchitecture);

		return model;
	}

	private ComponentTypeArchitectureModel componentTypeArchitecture(ComponentTypeArchitectureDTO dto) {
		return componentTypeArchitectureRepository.repository()
				.findByComponentTypeAndArchitectureAndNetworkAndDeploymentTypeAndPlatformAndLanguage(
						dto.getComponentType(), dto.getArchitecture(), dto.getNetwork(), dto.getDeploymentType(),
						dto.getPlatform(), dto.getLanguage())
				.orElseThrow(() -> new JpaNotFoundException(ExceptionCodes.NOT_FOUND_COMPONENTTYPE_ARCHITECTURE,
						String.format("no result found for component type architecture %s, %s, %s, %s, %s, %s ",
								dto.getComponentType(), dto.getArchitecture(), dto.getNetwork(),
								dto.getDeploymentType(), dto.getPlatform(), dto.getLanguage())));
	}
}
