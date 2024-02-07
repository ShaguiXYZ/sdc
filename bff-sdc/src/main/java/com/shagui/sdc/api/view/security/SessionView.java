package com.shagui.sdc.api.view.security;

import com.shagui.sdc.api.dto.security.SessionDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SessionView {
	private String sid;
	private String token;

	public SessionView(SessionDTO source) {
		this.sid = source.getSid();
		this.token = source.getToken();
	}
}
