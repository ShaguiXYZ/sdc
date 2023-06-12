package com.shagui.sdc.api.client;

import java.net.URI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import feign.Response;

@FeignClient(name = "sonar-service", url = "${services.sonar.uri}", primary = false)
public interface SonarClient {
	@GetMapping("components/search")
	Response components(URI baseUrl, @RequestParam String qualifiers);

	@GetMapping("measures/search")
	Response measures(URI baseUrl, @RequestParam String projectKeys,
			@RequestParam String metricKeys);

	default Response components(URI baseUri) {
		return components(baseUri, "TRK");
	}

	@GetMapping("components/search")
	Response components(@RequestParam String qualifiers);

	@GetMapping("measures/search")
	Response measures(@RequestParam String projectKeys, @RequestParam String metricKeys);
}