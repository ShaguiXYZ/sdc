package com.shagui.sdc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.UriRestApi;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.service.UriService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "uris", description = "API to maintain Uris")
public class UriController implements UriRestApi {
	private UriService uriService;

	@Override
	public List<UriModel> uris() {
		return uriService.availables();
	}
}
