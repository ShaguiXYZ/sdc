package com.shagui.sdc.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shagui.sdc.json.model.ComponentParamsModel;
import com.shagui.sdc.json.model.DataListModel;
import com.shagui.sdc.json.model.UriModel;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StaticRepositoryConfig {
	private ObjectMapper mapper;

	private List<UriModel> uris = new ArrayList<>();
	private List<DataListModel> datalists = new ArrayList<>();
	private List<ComponentParamsModel> componentParams = new ArrayList<>();

	public StaticRepositoryConfig(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@PostConstruct
	public void init() {
		uris = loadResource("data/uris.json", UriModel[].class).map(Arrays::asList).orElseGet(ArrayList::new);
		datalists = loadResource("data/datalists.json", DataListModel[].class).map(Arrays::asList)
				.orElseGet(ArrayList::new);
		componentParams = loadResource("data/component-params.json", ComponentParamsModel[].class).map(Arrays::asList)
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

	private <T> Optional<T> loadResource(@NonNull String resource, Class<T> clazz) {
		return mapResource(new ClassPathResource(resource), clazz);
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
