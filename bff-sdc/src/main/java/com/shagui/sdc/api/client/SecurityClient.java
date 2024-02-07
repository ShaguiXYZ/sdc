package com.shagui.sdc.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.shagui.sdc.api.dto.security.SessionDTO;
import com.shagui.sdc.api.dto.security.UserDTO;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.util.http.HttpServletRequestUtils;

import io.swagger.v3.oas.annotations.Parameter;

@FeignClient(name = "security-service", url = "${services.rws-security}", primary = false)
public interface SecurityClient {
	@PostMapping("public/login")
	SessionDTO login(@RequestParam("resource") String resource, @RequestParam("username") String username,
			@RequestParam("password") String pwd);

	@PutMapping("logout")
	SessionDTO closeSession(
			@RequestHeader(value = HttpServletRequestUtils.HEADER_AUTHORIZATION, required = true) String authorizationHeader,
			@RequestHeader(value = HttpServletRequestUtils.HEADER_SESSION_ID, required = true) String sidHeader);

	@GetMapping("findUser/{userName}")
	UserDTO findUser(
			@RequestHeader(value = HttpServletRequestUtils.HEADER_AUTHORIZATION, required = true) String authorizationHeader,
			@RequestHeader(value = HttpServletRequestUtils.HEADER_SESSION_ID, required = true) String sidHeader,
			@PathVariable("userName") @Parameter(description = "Unser name (UID)") String userName);

	@GetMapping("ping")
	UserDTO ping(
			@RequestHeader(value = HttpServletRequestUtils.HEADER_AUTHORIZATION, required = true) String authorizationHeader,
			@RequestHeader(value = HttpServletRequestUtils.HEADER_SESSION_ID, required = true) String sidHeader);

	default UserDTO authUser() {
		return ping(authorizationHeader(), sidHeader());
	}

	default UserDTO findUser(String userName) {
		return findUser(authorizationHeader(), sidHeader(),
				userName);
	}

	default SessionDTO logout() {
		return closeSession(authorizationHeader(), sidHeader());
	}

	private String authorizationHeader() {
		return HttpServletRequestUtils.getAuthorizationHeader()
				.orElseThrow(() -> new SdcCustomException("Authorization header not found"));
	}

	private String sidHeader() {
		return HttpServletRequestUtils.getSIDHeader()
				.orElseThrow(() -> new SdcCustomException("SID header not found"));
	}
}