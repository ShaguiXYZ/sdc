package com.shagui.sdc.api.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Parameter;

@FeignClient(name = "rws-sdc-utils", url = "${services.rws-sdc}", primary = false)
public interface RwsSdcUtilsClient {
	@GetMapping("utils/languageDistribution/{componentId}")
	Map<String, String> languageDistribution(
			@PathVariable @Parameter(description = "Component identifier") int componentId);
}