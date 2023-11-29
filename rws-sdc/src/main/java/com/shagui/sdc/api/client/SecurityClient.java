package com.shagui.sdc.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.shagui.sdc.api.dto.security.UserDTO;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.HttpServletRequestUtils;

@FeignClient(name = "security-service", url = "${rest.security.issuerUri}", primary = false)
public interface SecurityClient {
	@GetMapping("ping")
	UserDTO ping(
			@RequestHeader(value = Ctes.HEADER_AUTHORIZATION, required = true) String authorizationHeader,
			@RequestHeader(value = Ctes.HEADER_SESSION_ID, required = true) String sidHeader);

	default UserDTO authUser() {
		return ping(HttpServletRequestUtils.getAuthorizationHeader(), HttpServletRequestUtils.getSIDHeader());
	}
}