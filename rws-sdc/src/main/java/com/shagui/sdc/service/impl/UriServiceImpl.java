package com.shagui.sdc.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.service.UriService;
import com.shagui.sdc.util.UrlUtils;
import com.shagui.sdc.util.jpa.JpaCommonRepository;

@Service
public class UriServiceImpl implements UriService {
	private JpaCommonRepository<ComponentRepository, ComponentModel, Integer> componentRepository;

	public UriServiceImpl(final ComponentRepository componentRepository) {
		this.componentRepository = () -> componentRepository;
	}

	@Override
	public List<UriModel> availables() {
		return StaticRepository.uris().values().stream().toList();
	}

	@Override
	public UriModel componentUri(int componentId, UriType type) {
		ComponentModel componentModel = componentRepository.findExistingId(componentId);

		return UrlUtils.componentUri(componentModel, type).orElseThrow(() -> new SdcCustomException(
				"Uri not found for component %s and type %s".formatted(componentModel.getName(), type)));
	}
}
