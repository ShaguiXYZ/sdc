package com.shagui.sdc.core.configuration.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;

import com.shagui.sdc.api.client.SecurityClient;
import com.shagui.sdc.api.dto.security.UserDTO;
import com.shagui.sdc.util.UrlUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "rest.security")
public class SecurityProperties {
	private final SecurityClient securityClient;

	@Getter()
	@Setter()
	private boolean enabled;

	@Getter()
	@Setter()
	private CorsConfiguration cors;

	private List<String> publicApis = new ArrayList<>();
	private List<String> noRoleApis = new ArrayList<>();
	private Map<String, List<String>> rolesByApi = new HashMap<>();

	@Getter()
	private HttpStatus status = HttpStatus.UNAUTHORIZED;

	public SecurityProperties(SecurityClient securityClient) {
		this.securityClient = securityClient;
	}

	public void setApiMatcher(List<ApiMatcher> apiMatcher) {
		this.publicApis = apiMatcher.stream()
				.filter(ApiMatcher::isPublic)
				.map(ApiMatcher::getApi)
				.toList();

		this.rolesByApi = apiMatcher.stream()
				.filter(api -> !api.isPublic())
				.collect(HashMap::new, (map, api) -> map.put(api.getApi(), api.getRoles()), HashMap::putAll);

		this.noRoleApis = rolesByApi.entrySet().stream()
				.filter(entry -> CollectionUtils.isEmpty(entry.getValue()))
				.map(Map.Entry::getKey)
				.toList();

		this.rolesByApi = this.rolesByApi.entrySet().stream()
				.filter(entry -> !CollectionUtils.isEmpty(entry.getValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public CorsConfiguration getCorsConfiguration() {
		return cors;
	}

	public boolean isAuthorizedRequest(HttpServletRequest request) {
		if (isPublicRequest(request)) {
			return true;
		}

		Optional<UserDTO> user = securityClient.authUser();
		if (user.isEmpty()) {
			this.status = HttpStatus.UNAUTHORIZED;
			return false;
		}
		List<String> allowedRoles = rolesByApi.entrySet().stream()
				.filter(entry -> UrlUtils.matchesPath(request).test(entry.getKey()))
				.flatMap(entry -> entry.getValue().stream()).toList();

		if (CollectionUtils.isEmpty(allowedRoles)) {
			this.status = HttpStatus.UNAUTHORIZED;
			return noRoleApis.stream().anyMatch(UrlUtils.matchesPath(request));
		} else {
			this.status = HttpStatus.LOCKED;
			return user.get().getAuthorities().stream()
					.anyMatch(authority -> allowedRoles.contains(authority.getAuthority()));
		}
	}

	private boolean isPublicRequest(HttpServletRequest request) {
		return publicApis.stream().anyMatch(UrlUtils.matchesPath(request));
	}

	@Getter()
	@Setter()
	public static class ApiMatcher {
		String api;
		boolean isPublic;
		List<String> roles;
	}
}