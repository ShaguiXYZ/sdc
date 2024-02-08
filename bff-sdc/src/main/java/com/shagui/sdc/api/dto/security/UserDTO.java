package com.shagui.sdc.api.dto.security;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
public class UserDTO {
	private String userName;
	private String email;
	private PersonDTO person;
}
