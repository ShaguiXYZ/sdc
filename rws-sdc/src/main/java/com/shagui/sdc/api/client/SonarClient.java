package com.shagui.sdc.api.client;

import java.net.URI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import feign.Response;

@FeignClient(name = "sonar-service", url = "${services.no-uri}", primary = false)
public interface SonarClient {
	@GetMapping("measures/search")
	Response measures(URI baseUrl, @RequestParam String projectKeys,
			@RequestParam String metricKeys);

	@GetMapping("measures/search")
	Response measures(URI baseUrl, @RequestParam String projectKeys,
			@RequestParam String metricKeys,
			@RequestHeader(value = "Authorization", required = true) String authorizationHeader);
}