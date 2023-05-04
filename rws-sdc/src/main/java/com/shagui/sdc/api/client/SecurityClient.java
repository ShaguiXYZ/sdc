package com.shagui.sdc.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.shagui.sdc.api.dto.security.UserDTO;
import com.shagui.sdc.util.HttpServletRequestUtils;

@FeignClient(name = "security-service", url = "${services.security.uri}", primary = false)
public interface SecurityClient {
	@GetMapping("ping")
	UserDTO ping(
			@RequestHeader(value = HttpServletRequestUtils.HEADER_AUTHORIZATION, required = true) String authorizationHeader,
			@RequestHeader(value = HttpServletRequestUtils.HEADER_SESSION_ID, required = true) String sidHeader);

	default UserDTO authUser() {
		return ping(HttpServletRequestUtils.getAuthorizationHeader(), HttpServletRequestUtils.getSIDHeader());
	}
}