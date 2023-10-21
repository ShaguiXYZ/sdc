package com.shagui.sdc.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shagui.sdc.json.model.ComponentParamsModel;
import com.shagui.sdc.json.model.DataListModel;
import com.shagui.sdc.json.model.UriModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StaticRepositoryConfig {
	@Value("classpath:data/uris.json")
	private Resource jsonUris;

	@Value("classpath:data/datalists.json")
	private Resource jsonDatalists;

	@Value("classpath:data/component-params.json")
	private Resource jsonComponentParams;

	@Autowired
	private ObjectMapper mapper;

	private List<UriModel> uris = new ArrayList<>();
	private List<DataListModel> datalists = new ArrayList<>();
	private List<ComponentParamsModel> componentParams = new ArrayList<>();

	@PostConstruct
	public void init() {
		uris = mapResource(jsonUris, UriModel[].class).map(Arrays::asList).orElseGet(ArrayList::new);
		datalists = mapResource(jsonDatalists, DataListModel[].class).map(Arrays::asList).orElseGet(ArrayList::new);
		componentParams = mapResource(jsonComponentParams, ComponentParamsModel[].class).map(Arrays::asList)
				.orElseGet(ArrayList::new);

		StaticRepository.setConfig(this);
	}

	public List<UriModel> uris() {
		return uris;
	}

	public List<DataListModel> datalists() {
		return datalists;
	}

	public List<ComponentParamsModel> componentParams() {
		return componentParams;
	}

	private <T> Optional<T> mapResource(Resource resource, Class<T> clazz) {
		try (InputStream is = resource.getInputStream()) {
			return Optional.of(mapper.readValue(is, clazz));
		} catch (IOException | IllegalArgumentException e) {
			log.error("{} not found.", resource.getFilename(), e);
			return Optional.empty();
		}
	}
}
