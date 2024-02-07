package com.shagui.sdc.api.dto.security;

import com.shagui.sdc.api.view.security.SessionView;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter()
@Setter()
@NoArgsConstructor
public class SessionDTO {
	private String sid;
	private String token;

	public SessionDTO(SessionView source) {
		this.sid = source.getSid();
		this.token = source.getToken();
	}
}
