package com.shagui.sdc.api.client;

import java.net.URI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import feign.Response;
import io.swagger.v3.oas.annotations.Parameter;

@FeignClient(name = "git-service", url = "${services.git.uri}", primary = false)
public interface GitClient {
	@GetMapping
	Response repoFile(URI baseUrl);

	@GetMapping
	Response repoFile(URI baseUrl,
			@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

	@GetMapping("repos/{owner}/{repo}/contents/{path}")
	Response repoFile(
			@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
			@PathVariable(value = "owner") @Parameter(description = "Repository owner") String owner,
			@PathVariable(value = "repo") @Parameter(description = "Repository name") String repo,
			@PathVariable(value = "path") @Parameter(description = "Repository path") String path);
}