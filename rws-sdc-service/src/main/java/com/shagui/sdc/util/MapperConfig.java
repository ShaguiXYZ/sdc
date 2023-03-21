package com.shagui.sdc.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shagui.sdc.service.SquadService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MapperConfig {
	private ObjectMapper objectMapper;
	
	@Autowired
	public SquadService squadService;

	@PostConstruct
	public void init() {
		Mapper.setConfig(this);
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

}
