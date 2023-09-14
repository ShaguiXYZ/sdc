package com.shagui.sdc.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shagui.sdc.api.client.RwsSdcUtilsClient;
import com.shagui.sdc.service.AnalysisUtilsService;

@Service
public class AnalysisUtilsServiceImpl implements AnalysisUtilsService {

	@Autowired
	private RwsSdcUtilsClient rwsSdcUtilsClient;

	@Override
	public Map<String, String> languageDistribution(int componentId) {
		return rwsSdcUtilsClient.languageDistribution(componentId);
	}

}
