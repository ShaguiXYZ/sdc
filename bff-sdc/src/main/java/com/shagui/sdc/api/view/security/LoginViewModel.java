package com.shagui.sdc.api.view.security;

import lombok.Data;

@Data
public class LoginViewModel {
	private String resource;
	private String userName;
	private String password;
}
