package com.shagui.sdc.api.client;

import java.net.URI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.dto.sonar.ComponentsSonarDTO;
import com.shagui.sdc.api.dto.sonar.MeasuresSonarDTO;

@FeignClient(name = "sonar-service", url = "${services.sonar.uri}", primary = false)
public interface SonarClient {
	@GetMapping("components/search")
	ComponentsSonarDTO components(URI baseUrl, @RequestParam("qualifiers") String qualifiers);

	@GetMapping("measures/search")
	MeasuresSonarDTO measures(URI baseUrl, @RequestParam("projectKeys") String projectKeys,
			@RequestParam("metricKeys") String metricKeys);

	default ComponentsSonarDTO components(URI baseUri) {
		return components(baseUri, "TRK");
	}

	@GetMapping("components/search")
	ComponentsSonarDTO components(@RequestParam("qualifiers") String qualifiers);

	@GetMapping("measures/search")
	MeasuresSonarDTO measures(@RequestParam("projectKeys") String projectKeys,
			@RequestParam("metricKeys") String metricKeys);
}