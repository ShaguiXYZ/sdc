package com.shagui.sdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.SonarApi;
import com.shagui.sdc.api.client.SonarClient;
import com.shagui.sdc.api.dto.PageableDTO;
import com.shagui.sdc.api.dto.SquadDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "sonar", description = "API to sonar calls")
public class SonarControler implements SonarApi {
	@Autowired
	private SonarClient sonarClient;

	@Override
	public PageableDTO<SquadDTO> components(int uriId, Integer page) {
		// TODO Auto-generated method stub
		return null;
	}

}
