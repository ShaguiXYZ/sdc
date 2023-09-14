package com.shagui.sdc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.AnalysisUtilsRestApi;
import com.shagui.sdc.service.AnalysisUtilsService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "analysis utils", description = "API get analysis information")
public class AnalysisUtilsControler implements AnalysisUtilsRestApi {
	@Autowired
	private AnalysisUtilsService analysisUtilsService;

	@Override
	public Map<String, String> languageDistribution(int componentId) {
		return analysisUtilsService.languageDistribution(componentId);
	}
}
