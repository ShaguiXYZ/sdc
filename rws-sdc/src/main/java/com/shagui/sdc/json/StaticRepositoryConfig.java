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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shagui.sdc.json.model.ComponentArchitectureConfigModel;
import com.shagui.sdc.json.model.ComponentParamsModel;
import com.shagui.sdc.json.model.DataListModel;
import com.shagui.sdc.json.model.UriModel;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StaticRepositoryConfig {
	@Value("${sdc.json-schema.uris:data/uris.json}")
	private String urisPath;

	@Value("${sdc.json-schema.dictionary:#{null}}")
	private String dictionaryPath;

	@Value("${sdc.json-schema.datalists:data/datalists.json}")
	private String datalistsPath;

	@Value("${sdc.json-schema.component-params:data/component-params.json}")
	private String componentParamsPath;

	@Value("${sdc.json-schema.component-architecture-config:#{null}}")
	private String componentArchitectureConfigPath;

	private final ObjectMapper mapper;

	private Map<String, UriModel> uris = new HashMap<>();
	private Map<String, String> dictionary = new HashMap<>();
	private List<DataListModel> datalists = new ArrayList<>();
	private List<ComponentParamsModel> componentParams = new ArrayList<>();
	private ComponentArchitectureConfigModel componentArchitectureConfig;

	public StaticRepositoryConfig(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		this.uris = loadResource(urisPath, UriModel[].class).map(Arrays::asList)
				.orElseGet(ArrayList::new).stream().collect(Collectors.toMap(UriModel::getName, u -> u));

		this.dictionary = loadResource(dictionaryPath, HashMap.class).orElseGet(HashMap::new);

		this.datalists = loadResource(datalistsPath, DataListModel[].class).map(Arrays::asList)
				.orElseGet(ArrayList::new);

		this.componentParams = loadResource(componentParamsPath, ComponentParamsModel[].class)
				.map(Arrays::asList)
				.orElseGet(ArrayList::new);

		this.componentArchitectureConfig = loadResource(componentArchitectureConfigPath,
				ComponentArchitectureConfigModel.class).orElseGet(ComponentArchitectureConfigModel::new);

		StaticRepository.setConfig(this);
	}

	public Map<String, String> dictionary() {
		return dictionary;
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

	public ComponentArchitectureConfigModel componentArchitectureConfig() {
		return componentArchitectureConfig;
	}

	private <T> Optional<T> loadResource(String resourcePath, Class<T> clazz) {
		if (StringUtils.hasText(resourcePath)) {
			Resource resource = new ClassPathResource(resourcePath);

			try (InputStream is = resource.getInputStream()) {
				return Optional.of(mapper.readValue(is, clazz));
			} catch (IOException | IllegalArgumentException e) {
				log.error("{} not found.", resource.getFilename(), e);
				return Optional.empty();
			}
		} else {
			return Optional.empty();
		}
	}
}
