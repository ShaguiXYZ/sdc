package com.shagui.sdc.api.dto.security;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
public class UserDTO {
	private String userName;
	private String email;
	private List<String> authorities;
	private PersonDTO person;
}
