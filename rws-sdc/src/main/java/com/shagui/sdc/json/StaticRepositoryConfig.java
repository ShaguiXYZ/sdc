package com.shagui.sdc.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
	private final ObjectMapper mapper;

	private Map<String, UriModel> uris = new HashMap<>();
	private List<DataListModel> datalists = new ArrayList<>();
	private List<ComponentParamsModel> componentParams = new ArrayList<>();

	public StaticRepositoryConfig(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@PostConstruct
	public void init() {
		this.uris = loadResource("data/uris.json", UriModel[].class).map(Arrays::asList)
				.orElseGet(ArrayList::new).stream().collect(Collectors.toMap(UriModel::getName, u -> u));

		this.datalists = loadResource("data/datalists.json", DataListModel[].class).map(Arrays::asList)
				.orElseGet(ArrayList::new);
		this.componentParams = loadResource("data/component-params.json", ComponentParamsModel[].class)
				.map(Arrays::asList)
				.orElseGet(ArrayList::new);

		StaticRepository.setConfig(this);
	}

	public Map<String, UriModel> uris() {
		return uris;
	}

	public List<DataListModel> datalists() {
		return datalists;
	}

	public List<ComponentParamsModel> componentParams() {
		return componentParams;
	}

	private <T> Optional<T> loadResource(@NonNull String resourcePath, Class<T> clazz) {
		Resource resource = new ClassPathResource(resourcePath);

		try (InputStream is = resource.getInputStream()) {
			return Optional.of(mapper.readValue(is, clazz));
		} catch (IOException | IllegalArgumentException e) {
			log.error("{} not found.", resource.getFilename(), e);
			return Optional.empty();
		}
	}
}
