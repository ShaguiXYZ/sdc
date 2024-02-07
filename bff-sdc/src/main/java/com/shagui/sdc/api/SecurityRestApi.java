package com.shagui.sdc.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shagui.sdc.api.view.security.LoginViewModel;
import com.shagui.sdc.api.view.security.SessionView;
import com.shagui.sdc.api.view.security.UserView;

import feign.Headers;

@Headers("Content-Type: application/json;charset=UTF-8")
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
public interface SecurityRestApi {
	@PostMapping("login")
	SessionView login(@RequestBody LoginViewModel login);

	@PutMapping("logout")
	SessionView logout();

	@GetMapping("authUser")
	UserView authUser();
}
