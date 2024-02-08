package com.shagui.sdc.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shagui.sdc.api.SecurityRestApi;
import com.shagui.sdc.api.client.SecurityClient;
import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.dto.security.SessionDTO;
import com.shagui.sdc.api.dto.security.UserDTO;
import com.shagui.sdc.api.view.security.LoginViewModel;
import com.shagui.sdc.api.view.security.SessionView;
import com.shagui.sdc.api.view.security.UserView;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "security", description = "API calls for security actions")
@AllArgsConstructor
public class SecurityRestController implements SecurityRestApi {
	private final SecurityClient securityClient;

	@Override
	public SessionView login(LoginViewModel login) {
		SessionDTO dto = null;

		try {
			dto = securityClient.login(login.getResource(), login.getUserName(), login.getPassword());
		} catch (Exception e) {
			log.error("Error login:", e);

			throw e;
		}

		return CastFactory.getInstance(SessionView.class).parse(dto);
	}

	@Override
	public UserView authUser() {
		UserDTO user = securityClient.authUser();
		return CastFactory.getInstance(UserView.class).parse(securityClient.findUser(user.getUserName()));
	}

	@Override
	public SessionView logout() {
		return CastFactory.getInstance(SessionView.class).parse(securityClient.logout());
	}
}
