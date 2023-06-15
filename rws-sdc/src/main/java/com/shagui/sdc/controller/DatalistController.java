package com.shagui.sdc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.DatalistApi;
import com.shagui.sdc.service.DatalistService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "datalists", description = "API to maintain Datalists")
public class DatalistController implements DatalistApi {
	@Autowired
	private DatalistService datalistService;

	@Override
	public List<String> availables() {
		return datalistService.availables();
	}

	@Override
	public List<String> datalistValues(String datalist) {
		return datalistService.datalistValues(datalist);
	}

}
