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
import com.shagui.sdc.json.data.DataListModel;
import com.shagui.sdc.json.data.UriModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StaticRepositoryConfig {
	@Value("classpath:data/uris.json")
	private Resource jsonUris;

	@Value("classpath:data/datalists.json")
	private Resource jsonDatalists;

	@Autowired
	private ObjectMapper mapper;

	private List<UriModel> uris = new ArrayList<>();
	private List<DataListModel> datalists = new ArrayList<>();

	@PostConstruct
	public void init() {
		uris = mapResource(jsonUris, UriModel[].class).map(Arrays::asList).orElse(new ArrayList<>());
		datalists = mapResource(jsonDatalists, DataListModel[].class).map(Arrays::asList).orElse(new ArrayList<>());

		StaticRepository.setConfig(this);
	}

	public List<UriModel> uris() {
		return uris;
	}

	public List<DataListModel> datalists() {
		return datalists;
	}

	private <T> Optional<T> mapResource(Resource resource, Class<T> clazz) {
		try (InputStream is = resource.getInputStream()) {
			T input = mapper.readValue(is, clazz);
			return Optional.of(input);
		} catch (IOException e) {
			log.error(resource.getFilename() + " not found.", e);
			return Optional.ofNullable(null);
		}
	}
}
